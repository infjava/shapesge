package fri.shapesge.engine;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.AWTEvent;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;

class GameWindow {
    private static final GraphicsDevice DEVICE = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    private final JFrame frame;
    private final GamePanel gamePanel;
    private final GameObjects gameObjects;
    private final GameInputProcessor gameInputProcessor;
    private final GameFPSCounter fpsCounter;
    private final int width;
    private final int height;
    private final GameEventDispatcher gameEventDispatcher;
    private final Color backgroundColor;
    private final boolean showInfo;
    private final boolean isFullscreen;
    private final GameOnCloseOperation onCloseOperation;

    GameWindow(GameObjects gameObjects, GameInputProcessor gameInputProcessor, GameFPSCounter fpsCounter, GameConfig gameConfig, GameParser gameParser, GameEventDispatcher gameEventDispatcher) {
        this.gameObjects = gameObjects;
        this.gameInputProcessor = gameInputProcessor;
        this.fpsCounter = fpsCounter;
        this.gameEventDispatcher = gameEventDispatcher;

        this.width = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_WIDTH);
        this.height = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_HEIGHT);
        this.backgroundColor = gameParser.parseColor(gameConfig.get(GameConfig.WINDOW_SECTION, GameConfig.CANVAS_BACKGROUND));
        this.showInfo = gameConfig.getBoolean(GameConfig.WINDOW_SECTION, GameConfig.SHOW_INFO);
        this.isFullscreen = gameConfig.getBoolean(GameConfig.WINDOW_SECTION, GameConfig.FULLSCREEN);

        if (gameConfig.contains(GameConfig.WINDOW_SECTION, GameConfig.EXIT_ON_CLOSE)) {
            System.out.format("ShapesGE: Using deprecated %s setting, use %s instead", GameConfig.EXIT_ON_CLOSE, GameConfig.ON_CLOSE);

            this.onCloseOperation = new GameOnCloseOperation(
                gameConfig.getBoolean(GameConfig.WINDOW_SECTION, GameConfig.EXIT_ON_CLOSE) ? GameOnCloseOperationType.EXIT : GameOnCloseOperationType.HIDE,
                null
            );
        } else {
            this.onCloseOperation = gameParser.parseOnClose(gameConfig.get(GameConfig.WINDOW_SECTION, GameConfig.ON_CLOSE));
        }

        this.gamePanel = new GamePanel();

        var windowTitle = gameConfig.get(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_TITLE);
        this.frame = new JFrame(windowTitle);
        this.frame.setLayout(new GridLayout());
        this.frame.add(this.gamePanel);

        switch (this.onCloseOperation.getType()) {
            case EXIT:
                this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                break;
            case HIDE:
                this.frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                break;
            case SEND_MESSAGE:
                this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                this.frame.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        GameWindow.this.windowClosing();
                    }
                });
                break;
            default:
                this.frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
                break;
        }

        if (this.isFullscreen) {
            this.frame.setUndecorated(true);
            this.frame.pack();
            DEVICE.setFullScreenWindow(this.frame);
        } else {
            this.frame.pack();
            this.frame.setResizable(false);
        }
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public void redraw() {
        this.gamePanel.redraw();
    }

    private void windowClosing() {
        if (this.onCloseOperation.getType() != GameOnCloseOperationType.SEND_MESSAGE || this.onCloseOperation.getMessage() == null) {
            return;
        }

        this.gameEventDispatcher.dispatchStandard(this.onCloseOperation.getMessage());
    }

    public void showIfNeeded() {
        if (this.onCloseOperation.getType() == GameOnCloseOperationType.HIDE) {
            if (!this.frame.isVisible()) {
                this.frame.setVisible(true);
            }
        }
    }

    private class GamePanel extends Canvas {
        private AffineTransform canvasTransform;
        private AffineTransform invertedCanvasTransform;
        private boolean covered;

        GamePanel() {
            this.setPreferredSize(new Dimension(GameWindow.this.width, GameWindow.this.height));
            this.setFocusable(true);
            this.enableEvents(AWTEvent.MOUSE_EVENT_MASK | AWTEvent.MOUSE_MOTION_EVENT_MASK | AWTEvent.KEY_EVENT_MASK);

            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    GamePanel.this.resized();
                }
            });

            this.resized();
        }

        private void resized() {
            var correctSize = this.getWidth() == GameWindow.this.width && this.getHeight() == GameWindow.this.height;
            var invalidSize = this.getWidth() == 0 || this.getHeight() == 0;

            if (!GameWindow.this.isFullscreen || correctSize || invalidSize) {
                this.canvasTransform = new AffineTransform();
                this.invertedCanvasTransform = this.canvasTransform;
                this.covered = true;
                return;
            }

            var widthAspectRatio = (double)this.getWidth() / GameWindow.this.width;
            var heightAspectRatio = (double)this.getHeight() / GameWindow.this.height;

            this.covered = widthAspectRatio == heightAspectRatio;

            var transform = new AffineTransform();
            if (widthAspectRatio < heightAspectRatio) {
                transform.translate(0, (this.getHeight() - GameWindow.this.height * widthAspectRatio) / 2.0);
                transform.scale(widthAspectRatio, widthAspectRatio);
            } else {
                transform.translate((this.getWidth() - GameWindow.this.width * heightAspectRatio) / 2.0, 0);
                transform.scale(heightAspectRatio, heightAspectRatio);
            }

            this.canvasTransform = transform;

            try {
                this.invertedCanvasTransform = transform.createInverse();
            } catch (NoninvertibleTransformException e) {
                throw new RuntimeException(e);
            }
        }

        private void draw(final Graphics2D canvas) {
            if (!this.covered) {
                canvas.setBackground(Color.black);
                canvas.clearRect(0, 0, this.getWidth(), this.getHeight());
            }

            if (!this.canvasTransform.isIdentity()) {
                canvas.transform(this.canvasTransform);
            }

            canvas.setBackground(GameWindow.this.backgroundColor);
            canvas.clearRect(0, 0, GameWindow.this.width, GameWindow.this.height);
            canvas.setClip(0, 0, GameWindow.this.width, GameWindow.this.height);

            GameWindow.this.gameObjects.drawAll(canvas);

            if (GameWindow.this.showInfo) {
                canvas.setColor(Color.black);
                canvas.setXORMode(Color.white);

                canvas.setFont(new Font("Serif", Font.PLAIN, 12 ));
                canvas.drawString(
                        String.format(
                                "FPS: %d, Objects: %d",
                                GameWindow.this.fpsCounter.getFPS(),
                                GameWindow.this.gameObjects.getCount()
                        ),
                        1,
                        12
                );

                canvas.setPaintMode();
            }
        }

        public synchronized void redraw() {
            // Check if component is displayable (has a valid peer) before creating buffer strategy
            // This prevents IllegalStateException on macOS where component may not be ready yet
            if (!this.isDisplayable()) {
                return;
            }

            var bufferStrategy = this.getBufferStrategy();
            if (bufferStrategy == null) {
                this.createBufferStrategy(2);
                bufferStrategy = this.getBufferStrategy();
            }

            do {
                do {
                    final var canvas = (Graphics2D)bufferStrategy.getDrawGraphics();
                    try {
                        this.draw(canvas);
                    } finally {
                        canvas.dispose();
                    }
                } while (bufferStrategy.contentsRestored());

                bufferStrategy.show();
            } while (bufferStrategy.contentsLost());
        }

        @Override
        protected void processKeyEvent(KeyEvent e) {
            super.processKeyEvent(e);
            GameWindow.this.gameInputProcessor.processKeyEvent(e);
        }

        @Override
        protected void processMouseEvent(MouseEvent e) {
            super.processMouseEvent(e);

            var transformedEvent = this.transformMouseEvent(e);
            if (!this.isOnCanvas(transformedEvent)) {
                return;
            }
            GameWindow.this.gameInputProcessor.processMouseEvent(transformedEvent);
        }

        @Override
        protected void processMouseMotionEvent(MouseEvent e) {
            super.processMouseMotionEvent(e);

            var transformedEvent = this.transformMouseEvent(e);
            if (!this.isOnCanvas(transformedEvent)) {
                return;
            }
            GameWindow.this.gameInputProcessor.processMouseEvent(transformedEvent);
        }

        private MouseEvent transformMouseEvent(MouseEvent e) {
            var xy = new Point2D.Double(e.getX(), e.getY());
            this.invertedCanvasTransform.transform(xy, xy);

            return new MouseEvent(
                    e.getComponent(),
                    e.getID(),
                    e.getWhen(),
                    e.getModifiersEx(),
                    (int)Math.round(xy.getX()),
                    (int)Math.round(xy.getY()),
                    e.getXOnScreen(),
                    e.getYOnScreen(),
                    e.getClickCount(),
                    e.isPopupTrigger(),
                    e.getButton()
            );
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        private boolean isOnCanvas(MouseEvent e) {
            return e.getX() >= 0
                    && e.getY() >= 0
                    && e.getX() < GameWindow.this.width
                    && e.getY() < GameWindow.this.height;
        }
    }
}
