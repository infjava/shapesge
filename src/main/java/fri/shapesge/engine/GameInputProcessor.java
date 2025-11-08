package fri.shapesge.engine;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

class GameInputProcessor {
    private final ArrayList<GameKeyEvent> keyEvents;
    private final ArrayList<GameMouseEvent> mouseEvents;
    private final GameEventDispatcher eventDispatcher;

    GameInputProcessor(GameEventDispatcher eventDispatcher, GameConfig gameConfig, GameParser gameParser) {
        this.eventDispatcher = eventDispatcher;
        this.keyEvents = new ArrayList<>();
        this.mouseEvents = new ArrayList<>();

        for (var message : gameConfig.getOptions(GameConfig.KEYBOARD_SECTION)) {
            var messageName = message.getOption();
            var eventDefinitions = message.getValue().split(",");
            for (String eventDefinition : eventDefinitions) {
                var keyEvent = gameParser.parseKeyEvent(eventDefinition, messageName);
                this.registerKeyEvent(keyEvent);
            }
        }

        for (var message : gameConfig.getOptions(GameConfig.MOUSE_SECTION)) {
            var messageName = message.getOption();
            var eventDefinitions = message.getValue().split(",");
            for (String eventDefinition : eventDefinitions) {
                var mouseEvent = gameParser.parseMouseEvent(eventDefinition, messageName);
                this.registerMouseEvent(mouseEvent);
            }
        }
    }

    public void registerKeyEvent(GameKeyEvent keyEvent) {
        this.keyEvents.add(keyEvent);
    }

    public void registerMouseEvent(GameMouseEvent mouseEvent) {
        this.mouseEvents.add(mouseEvent);
    }

    public void processKeyEvent(KeyEvent awtEvent) {
        for (GameKeyEvent event : this.keyEvents) {
            if (event.matches(awtEvent)) {
                this.eventDispatcher.dispatchStandard(event.getMessage());
            }
        }
    }

    public void processMouseEvent(MouseEvent awtEvent) {
        for (GameMouseEvent event : this.mouseEvents) {
            if (event.matches(awtEvent)) {
                this.eventDispatcher.dispatchMouse(event.getMessage(), awtEvent.getX(), awtEvent.getY());
            }
        }
    }
}
