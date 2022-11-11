package fri.shapesge;

class Game {
    private static final Game INSTANCE = new Game();

    public static Game getGame() {
        return Game.INSTANCE;
    }

    private final GameConfig gameConfig;
    private final GameFPSCounter gameFPSCounter;
    private final GameLoop gameLoop;
    private final GameEventDispatcher gameEventDispatcher;
    private final GameInputProcessor gameInputProcessor;
    private final GameTimerProcessor gameTimerProcessor;
    private final GameObjects gameObjects;
    private final GameWindow gameWindow;

    private Game() {
        this.gameConfig = new GameConfig();
        this.gameObjects = new GameObjects();
        this.gameEventDispatcher = new GameEventDispatcher();
        this.gameInputProcessor = new GameInputProcessor(
                this.gameEventDispatcher,
                this.gameConfig
        );
        this.gameTimerProcessor = new GameTimerProcessor(
                this.gameEventDispatcher,
                this.gameConfig
        );
        this.gameFPSCounter = new GameFPSCounter();
        this.gameWindow = new GameWindow(
                this.gameObjects,
                this.gameInputProcessor,
                this.gameFPSCounter,
                this.gameConfig
        );
        this.gameLoop = new GameLoop(
                this.gameWindow,
                this.gameTimerProcessor,
                this.gameEventDispatcher,
                this.gameFPSCounter,
                this.gameConfig
        );

        this.gameWindow.show();
        this.gameLoop.start();
    }

    public void registerShape(DrawableShape drawableShape) {
        this.gameObjects.registerShape(drawableShape);
    }

    public void registerEventTarget(Object target) {
        this.gameEventDispatcher.registerTarget(target);
    }
}
