package fri.shapesge.engine;

import java.awt.event.KeyEvent;

class GameKeyEvent {
    private static final int MODIFIER_MASK = KeyEvent.SHIFT_DOWN_MASK | KeyEvent.CTRL_DOWN_MASK | KeyEvent.ALT_DOWN_MASK | KeyEvent.META_DOWN_MASK;

    private final int eventType;
    private final int modifiers;
    private final int keyCode;
    private final String message;

    GameKeyEvent(int eventType, int modifiers, int keyCode, String message) {
        this.eventType = eventType;
        this.modifiers = modifiers;
        this.keyCode = keyCode;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean matches(KeyEvent keyEvent) {
        return this.eventType == keyEvent.getID()
                && this.modifiers == (keyEvent.getModifiersEx() & MODIFIER_MASK)
                && this.keyCode == keyEvent.getKeyCode();
    }
}
