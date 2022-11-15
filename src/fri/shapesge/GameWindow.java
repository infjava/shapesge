package fri.shapesge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;

class GameWindow {
    private static final GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];

    private final JFrame frame;
    private final GamePanel gamePanel;
    private final GameObjects gameObjects;
    private final GameInputProcessor gameInputProcessor;
    private final GameFPSCounter fpsCounter;
    private final int width;
    private final int height;
    private final Color backgroundColor;
    private final boolean showInfo;
    private final boolean isFullscreen;

    public GameWindow(GameObjects gameObjects, GameInputProcessor gameInputProcessor, GameFPSCounter fpsCounter, GameConfig gameConfig) {
        this.gameObjects = gameObjects;
        this.gameInputProcessor = gameInputProcessor;
        this.fpsCounter = fpsCounter;
        this.width = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_WIDTH);
        this.height = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_HEIGHT);
        this.backgroundColor = gameConfig.getColor(GameConfig.WINDOW_SECTION, GameConfig.CANVAS_BACKGROUND);
        this.showInfo = gameConfig.getBoolean(GameConfig.WINDOW_SECTION, GameConfig.SHOW_INFO);
        this.isFullscreen = gameConfig.getBoolean(GameConfig.WINDOW_SECTION, GameConfig.FULLSCREEN);

        this.gamePanel = new GamePanel();

        var windowTitle = gameConfig.get(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_TITLE);
        this.frame = new JFrame(windowTitle);
        this.frame.setLayout(new GridLayout());
        this.frame.add(this.gamePanel);
        if (this.isFullscreen) {
            this.frame.setUndecorated(true);
            this.frame.pack();
            device.setFullScreenWindow(this.frame);
        } else {
            this.frame.pack();
            this.frame.setResizable(false);
        }
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public void redraw() {
        this.gamePanel.repaint();
    }

    private class GamePanel extends JPanel {
        private AffineTransform canvasTransform;
        private boolean covered;

        public GamePanel() {
            this.setPreferredSize(new Dimension(GameWindow.this.width, GameWindow.this.height));
            this.setFocusable(true);
            this.enableEvents(AWTEvent.MOUSE_EVENT_MASK);

            this.addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    GamePanel.this.resized();
                }
            });

            this.resized();
        }

        private void resized() {
            if (!GameWindow.this.isFullscreen) {
                this.canvasTransform = new AffineTransform();
                this.covered = true;
                return;
            }

            if (this.getWidth() == GameWindow.this.width && this.getHeight() == GameWindow.this.height) {
                this.canvasTransform = new AffineTransform();
                this.covered = true;
                return;
            }

            var widthAspectRatio = (double) this.getWidth() / GameWindow.this.width;
            var heightAspectRatio = (double) this.getHeight() / GameWindow.this.height;

            this.covered = widthAspectRatio == heightAspectRatio;

            var transform = new AffineTransform();
            if (widthAspectRatio < heightAspectRatio) {
                transform.translate(0, (this.getHeight() - GameWindow.this.height) / 2.0);
                transform.scale(widthAspectRatio, widthAspectRatio);
            } else {
                transform.translate((this.getWidth() - GameWindow.this.width) / 2.0, 0);
                transform.scale(heightAspectRatio, heightAspectRatio);
            }

            this.canvasTransform = transform;
        }

        @Override
        public void paint(Graphics g) {
            final var canvas = (Graphics2D)g;

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

        @Override
        protected void processKeyEvent(KeyEvent e) {
            super.processKeyEvent(e);
            GameWindow.this.gameInputProcessor.processKeyEvent(e);
        }

        @Override
        protected void processMouseEvent(MouseEvent e) {
            super.processMouseEvent(e);
            GameWindow.this.gameInputProcessor.processMouseEvent(e);
        }

        @Override
        protected void processMouseMotionEvent(MouseEvent e) {
            super.processMouseMotionEvent(e);
            GameWindow.this.gameInputProcessor.processMouseEvent(e);
        }
    }
}
