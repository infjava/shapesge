package fri.shapesge;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

class GameWindow {
    private final JFrame frame;
    private final GamePanel gamePanel;
    private final GameObjects gameObjects;
    private final GameInputProcessor gameInputProcessor;
    private final int width;
    private final int height;
    private final Color backgroundColor;
    private final boolean showInfo;
    private int currentFPS;

    public GameWindow(GameObjects gameObjects, GameInputProcessor gameInputProcessor, GameConfig gameConfig) {
        this.gameObjects = gameObjects;
        this.gameInputProcessor = gameInputProcessor;
        this.width = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_WIDTH);
        this.height = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_HEIGHT);
        this.backgroundColor = gameConfig.getColor(GameConfig.WINDOW_SECTION, GameConfig.CANVAS_BACKGROUND);
        this.showInfo = gameConfig.getBoolean(GameConfig.WINDOW_SECTION, GameConfig.SHOW_INFO);

        this.gamePanel = new GamePanel();

        var windowTitle = gameConfig.get(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_TITLE);
        this.frame = new JFrame(windowTitle);
        this.frame.add(this.gamePanel);
        this.frame.pack();
        this.frame.setResizable(false);
    }

    public void show() {
        this.frame.setVisible(true);
    }

    public void redraw() {
        this.gamePanel.repaint();
    }

    public void notifyFPS(int fps) {
        this.currentFPS = fps;
    }

    private class GamePanel extends JPanel {
        public GamePanel() {
            this.setPreferredSize(new Dimension(GameWindow.this.width, GameWindow.this.height));
            this.setFocusable(true);
        }

        @Override
        public void paint(Graphics g) {
            final var canvas = (Graphics2D)g;

            canvas.setBackground(GameWindow.this.backgroundColor);
            canvas.clearRect(0, 0, GameWindow.this.width, GameWindow.this.height);

            GameWindow.this.gameObjects.drawAll(canvas);

            if (GameWindow.this.showInfo) {
                canvas.setColor(Color.black);
                canvas.setXORMode(Color.white);

                canvas.setFont(new Font("Serif", Font.PLAIN, 12 ));
                canvas.drawString(String.format("FPS: %d", GameWindow.this.currentFPS), 1, 12);

                canvas.setPaintMode();
            }
        }

        @Override
        protected void processKeyEvent(KeyEvent e) {
            super.processKeyEvent(e);
            GameWindow.this.gameInputProcessor.processKeyEvent(e);
        }
    }
}
