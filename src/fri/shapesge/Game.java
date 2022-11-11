package fri.shapesge;

class Game {
    private static final Game INSTANCE = new Game();

    public static Game getGame() {
        return Game.INSTANCE;
    }

    private final GameConfig gameConfig;
    private final GameLoop gameLoop;
    private final GameEventDispatcher gameEventQueue;
    private final GameInputProcessor gameInputProcessor;
    private final GameObjects gameObjects;
    private final GameWindow gameWindow;

    private Game() {
        this.gameConfig = new GameConfig();
        this.gameObjects = new GameObjects();
        this.gameEventQueue = new GameEventDispatcher();
        this.gameInputProcessor = new GameInputProcessor(
                this.gameEventQueue,
                this.gameConfig
        );
        this.gameWindow = new GameWindow(
                this.gameObjects,
                this.gameInputProcessor,
                this.gameConfig
        );
        this.gameLoop = new GameLoop(
                this.gameWindow,
                this.gameEventQueue,
                this.gameConfig
        );

        this.gameWindow.show();
        this.gameLoop.start();
    }

    public void registerShape(DrawableShape drawableShape) {
        this.gameObjects.registerShape(drawableShape);
    }

    public void registerEventTarget(Object target) {
        this.gameEventQueue.registerTarget(target);
    }
}
