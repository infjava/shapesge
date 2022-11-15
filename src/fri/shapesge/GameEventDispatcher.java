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

    public void deregisterTarget(Object target) {
        this.targets.remove(target);
    }

    public void dispatchStandard(String message) {
        this.eventQueue.add(new QueuedEvent(message));
    }

    public void dispatchMouse(String message, int x, int y) {
        this.eventQueue.add(new QueuedEvent(message, new Class[] {Integer.TYPE, Integer.TYPE}, new Object[] {x, y}));
    }

    public synchronized void doEvents() {
        while (!this.eventQueue.isEmpty()) {
            var event = this.eventQueue.pop();
            this.sendMessage(event);
        }
    }

    private void sendMessage(QueuedEvent event) {
        for (Object target : this.targets) {
            try {
                Method method = target.getClass().getMethod(event.message, event.parameterTypes);
                method.invoke(target, event.parameterValues);
            } catch (NoSuchMethodException e) {
                // do nothing here
            } catch (SecurityException | IllegalArgumentException | IllegalAccessException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    private static class QueuedEvent {
        private static final Class<?>[] EMPTY_PARAMETER_TYPES = new Class<?>[0];
        private static final Object[] EMPTY_PARAMETER_VALUES = new Object[0];

        public final String message;
        public final Class<?>[] parameterTypes;
        public final Object[] parameterValues;

        public QueuedEvent(String message) {
            this.message = message;
            this.parameterTypes = EMPTY_PARAMETER_TYPES;
            this.parameterValues = EMPTY_PARAMETER_VALUES;
        }

        public QueuedEvent(String message, Class<?>[] parameterTypes, Object[] parameterValues) {
            this.message = message;
            this.parameterTypes = parameterTypes;
            this.parameterValues = parameterValues;
        }
    }
}
