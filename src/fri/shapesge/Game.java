package fri.shapesge;

class Game {
    private static final Game INSTANCE = new Game();

    public static Game getGame() {
        return Game.INSTANCE;
    }

    static {
        Game.INSTANCE.start();
    }

    @SuppressWarnings("FieldCanBeLocal")
    private final GameConfig gameConfig;
    private final GameParser gameParser;
    @SuppressWarnings("FieldCanBeLocal")
    private final GameFPSCounter gameFPSCounter;
    private final GameLoop gameLoop;
    private final GameEventDispatcher gameEventDispatcher;
    @SuppressWarnings("FieldCanBeLocal")
    private final GameInputProcessor gameInputProcessor;
    @SuppressWarnings("FieldCanBeLocal")
    private final GameTimerProcessor gameTimerProcessor;
    private final GameObjects gameObjects;
    private final GameWindow gameWindow;

    private Game() {
        this.gameConfig = new GameConfig();
        this.gameParser = new GameParser(this.gameConfig);
        this.gameObjects = new GameObjects();
        this.gameEventDispatcher = new GameEventDispatcher();
        this.gameInputProcessor = new GameInputProcessor(
                this.gameEventDispatcher,
                this.gameConfig,
                this.gameParser
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
                this.gameConfig,
                this.gameParser
        );
        this.gameLoop = new GameLoop(
                this.gameWindow,
                this.gameTimerProcessor,
                this.gameEventDispatcher,
                this.gameFPSCounter,
                this.gameConfig
        );
    }

    private void start() {
        this.gameWindow.show();
        this.gameLoop.start();
    }

    public GameParser getParser() {
        return this.gameParser;
    }

    public void registerDrawable(GameDrawable drawable) {
        this.gameObjects.registerDrawable(drawable);
    }

    public void unregisterDrawable(GameDrawable drawable) {
        this.gameObjects.unregisterDrawable(drawable);
    }

    public void registerEventTarget(Object target) {
        this.gameEventDispatcher.registerTarget(target);
    }

    public void deregisterEventTarget(Object target) {
        this.gameEventDispatcher.deregisterTarget(target);
    }
}
