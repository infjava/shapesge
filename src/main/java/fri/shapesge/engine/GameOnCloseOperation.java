package fri.shapesge.engine;

public class GameOnCloseOperation {
    private final GameOnCloseOperationType type;
    private final String message;

    public GameOnCloseOperation(GameOnCloseOperationType type, String message) {
        this.type = type;
        this.message = message;
    }

    public GameOnCloseOperationType getType() {
        return this.type;
    }

    public String getMessage() {
        return this.message;
    }
}
