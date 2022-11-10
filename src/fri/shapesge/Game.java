package fri.shapesge;

class Game {
    private static final Game INSTANCE = new Game();
    private final GameLoop gameLoop;

    public static Game getGame() {
        return Game.INSTANCE;
    }

    private final GameConfig gameConfig;
    private final GameObjects gameObjects;
    private final GameWindow gameWindow;

    private Game() {
        this.gameConfig = new GameConfig();
        this.gameObjects = new GameObjects();
        this.gameWindow = new GameWindow(
                this.gameObjects,
                this.gameConfig
        );
        this.gameLoop = new GameLoop(this.gameConfig, this.gameWindow);

        this.gameWindow.show();
        this.gameLoop.start();
    }

    public void registerShape(DrawableShape drawableShape) {
        this.gameObjects.registerShape(drawableShape);
    }
}
