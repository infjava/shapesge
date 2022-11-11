package fri.shapesge;

class Game {
    private static final Game INSTANCE = new Game();

    public static Game getGame() {
        return Game.INSTANCE;
    }

    private final GameConfig gameConfig;
    private final GameLoop gameLoop;
    private final GameEvents gameEvents;
    private final GameObjects gameObjects;
    private final GameWindow gameWindow;

    private Game() {
        this.gameConfig = new GameConfig();
        this.gameObjects = new GameObjects();
        this.gameEvents = new GameEvents(this.gameConfig);
        this.gameWindow = new GameWindow(
                this.gameObjects,
                this.gameEvents,
                this.gameConfig
        );
        this.gameLoop = new GameLoop(
                this.gameConfig,
                this.gameWindow,
                this.gameEvents
        );

        this.gameWindow.show();
        this.gameLoop.start();
    }

    public void registerShape(DrawableShape drawableShape) {
        this.gameObjects.registerShape(drawableShape);
    }

    public void registerEventTarget(Object target) {
        this.gameEvents.registerTarget(target);
    }
}
