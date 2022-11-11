package fri.shapesge;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

class GameEvents {
    private final ArrayList<GameKeyEvent> keyEvents;
    private final GameEventDispatcher eventQueue;

    public GameEvents(GameEventDispatcher eventQueue, GameConfig gameConfig) {
        this.eventQueue = eventQueue;
        this.keyEvents = new ArrayList<>();

        for (String message : gameConfig.getOptions(GameConfig.KEYBOARD_SECTION)) {
            var eventDefinitions = gameConfig.get(GameConfig.KEYBOARD_SECTION, message).split(",");
            for (String eventDefinition : eventDefinitions) {
                var keyEvent = Parser.parseKeyEvent(eventDefinition, message);
                this.registerKeyEvent(keyEvent);
            }
        }
    }

    public synchronized void registerKeyEvent(GameKeyEvent keyEvent) {
        this.keyEvents.add(keyEvent);
    }

    public synchronized void processKeyEvent(KeyEvent awtEvent) {
        for (GameKeyEvent event : this.keyEvents) {
            if (event.matches(awtEvent)) {
                this.eventQueue.dispatch(event.getMessage());
            }
        }
    }
}
