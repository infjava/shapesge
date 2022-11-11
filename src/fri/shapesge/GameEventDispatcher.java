package fri.shapesge;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.ArrayList;

class GameEventDispatcher {
    private final ArrayList<Object> targets;
    private final ArrayDeque<QueuedEvent> eventQueue;

    public GameEventDispatcher() {
        this.targets = new ArrayList<>();
        this.eventQueue = new ArrayDeque<>();
    }

    public synchronized void registerTarget(Object target) {
        this.targets.add(target);
    }

    public void dispatch(String message) {
        this.eventQueue.add(new QueuedEvent(message));
    }

    public synchronized void doEvents() {
        while (!this.eventQueue.isEmpty()) {
            var event = this.eventQueue.pop();
            this.sendMessage(event.message);
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

    private static class QueuedEvent {
        public final String message;

        public QueuedEvent(String message) {
            this.message = message;
        }
    }
}
