package fri.shapesge;

import javax.swing.*;
import java.awt.*;

class GameWindow {
    private final JFrame frame;
    private final GameObjects gameObjects;
    private final int width;
    private final int height;
    private final Color backgroundColor;

    public GameWindow(GameObjects gameObjects, GameConfig gameConfig) {
        this.gameObjects = gameObjects;
        this.width = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_WIDTH);
        this.height = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_HEIGHT);
        this.backgroundColor = gameConfig.getColor(GameConfig.WINDOW_SECTION, GameConfig.CANVAS_BACKGROUND);

        var panel = new GamePanel();
        panel.setPreferredSize(new Dimension(this.width, this.height));

        var windowTitle = gameConfig.get(GameConfig.WINDOW_SECTION, GameConfig.WINDOW_TITLE);
        this.frame = new JFrame(windowTitle);
        this.frame.add(panel);
        this.frame.pack();
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    private class GamePanel extends JPanel {
        @Override
        public void paint(Graphics g) {
            final var canvas = (Graphics2D)g;

            canvas.setBackground(GameWindow.this.backgroundColor);
            canvas.clearRect(0, 0, GameWindow.this.width, GameWindow.this.height);

            GameWindow.this.gameObjects.drawAll(canvas);
        }
    }
}
