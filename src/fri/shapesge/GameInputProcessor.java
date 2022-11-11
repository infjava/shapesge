package fri.shapesge;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class GameInputProcessor {
    private final ArrayList<GameKeyEvent> keyEvents;
    private final ArrayList<GameMouseEvent> mouseEvents;
    private final GameEventDispatcher eventQueue;

    public GameInputProcessor(GameEventDispatcher eventQueue, GameConfig gameConfig) {
        this.eventQueue = eventQueue;
        this.keyEvents = new ArrayList<>();
        this.mouseEvents = new ArrayList<>();

        for (String message : gameConfig.getOptions(GameConfig.KEYBOARD_SECTION)) {
            var eventDefinitions = gameConfig.get(GameConfig.KEYBOARD_SECTION, message).split(",");
            for (String eventDefinition : eventDefinitions) {
                var keyEvent = Parser.parseKeyEvent(eventDefinition, message);
                this.registerKeyEvent(keyEvent);
            }
        }

        for (String message : gameConfig.getOptions(GameConfig.MOUSE_SECTION)) {
            var eventDefinitions = gameConfig.get(GameConfig.MOUSE_SECTION, message).split(",");
            for (String eventDefinition : eventDefinitions) {
                var mouseEvent = Parser.parseMouseEvent(eventDefinition, message);
                this.registerMouseEvent(mouseEvent);
            }
        }
    }

    public synchronized void registerKeyEvent(GameKeyEvent keyEvent) {
        this.keyEvents.add(keyEvent);
    }

    public synchronized void registerMouseEvent(GameMouseEvent mouseEvent) {
        this.mouseEvents.add(mouseEvent);
    }

    public synchronized void processKeyEvent(KeyEvent awtEvent) {
        for (GameKeyEvent event : this.keyEvents) {
            if (event.matches(awtEvent)) {
                this.eventQueue.dispatchStandard(event.getMessage());
            }
        }
    }

    public synchronized void processMouseEvent(MouseEvent awtEvent) {
        for (GameMouseEvent event : this.mouseEvents) {
            if (event.matches(awtEvent)) {
                this.eventQueue.dispatchMouse(event.getMessage(), awtEvent.getX(), awtEvent.getY());
            }
        }
    }
}
