package fri.shapesge;

import java.awt.event.KeyEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

class GameEvents {
    private final ArrayList<Object> targets;
    private final ArrayList<GameKeyEvent> keyEvents;

    public GameEvents(GameConfig gameConfig) {
        this.targets = new ArrayList<>();
        this.keyEvents = new ArrayList<>();

        for (String message : gameConfig.getOptions(GameConfig.KEYBOARD_SECTION)) {
            var eventDefinitions = gameConfig.get(GameConfig.KEYBOARD_SECTION, message).split(",");
            for (String eventDefinition : eventDefinitions) {
                var keyEvent = Parser.parseKeyEvent(eventDefinition, message);
                this.registerKeyEvent(keyEvent);
            }
        }
    }

    public void registerTarget(Object target) {
        this.targets.add(target);
    }

    public void registerKeyEvent(GameKeyEvent keyEvent) {
        this.keyEvents.add(keyEvent);
    }

    public void processKeyEvent(KeyEvent awtEvent) {
        for (GameKeyEvent event : this.keyEvents) {
            if (event.matches(awtEvent)) {
                this.sendMessage(event.getMessage());
            }
        }
    }

    private void sendMessage(String message) {
        for (Object target : this.targets) {
            try {
                Method method = target.getClass().getMethod(message);
                method.invoke(target);
            } catch (NoSuchMethodException e) {
                // do nothing here
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
