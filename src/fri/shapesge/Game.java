package fri.shapesge;

class Game {
    private static final Game INSTANCE = new Game();

    public static Game getGame() {
        return Game.INSTANCE;
    }

    private final GameConfig gameConfig;
    private final GameObjects gameObjects;

    private Game() {
        this.gameConfig = new GameConfig();
        this.gameObjects = new GameObjects();
    }

    public void registerShape(DrawableShape drawableShape) {
        this.gameObjects.registerShape(drawableShape);
    }
}
