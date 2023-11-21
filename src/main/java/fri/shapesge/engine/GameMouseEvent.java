package fri.shapesge.engine;

import java.awt.event.MouseEvent;

class GameMouseEvent {
    private final int eventType;
    private final int button;
    private final String message;

    GameMouseEvent(int eventType, int button, String message) {
        this.eventType = eventType;
        this.button = button;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean matches(MouseEvent mouseEvent) {
        if (this.eventType == MouseEvent.MOUSE_MOVED) {
            return mouseEvent.getID() == this.eventType;
        } else {
            return this.eventType == mouseEvent.getID()
                    && this.button == mouseEvent.getButton();
        }
    }
}
