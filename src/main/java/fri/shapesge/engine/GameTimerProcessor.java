package fri.shapesge.engine;

import java.util.ArrayList;


class GameTimerProcessor {
    private static final long MILLISECONDS = 1_000_000; // in nanoseconds
    private final GameEventDispatcher eventDispatcher;
    private final ArrayList<TimerDetail> timers;

    GameTimerProcessor(GameEventDispatcher eventDispatcher, GameConfig gameConfig) {
        this.eventDispatcher = eventDispatcher;
        this.timers = new ArrayList<>();

        var currentTime = System.nanoTime();
        for (var message : gameConfig.getOptions(GameConfig.TIMER_SECTION)) {
            this.registerTimer(currentTime, message.getIntValue(), message.getOption());
        }
    }

    private void registerTimer(long currentTime, int msInterval, String message) {
        this.timers.add(new TimerDetail(currentTime, msInterval * MILLISECONDS, message));
    }

    public synchronized void processTimers() {
        var currentTime = System.nanoTime();

        for (var timer : this.timers) {
            while (timer.match(currentTime)) {
                timer.advance();
                this.eventDispatcher.dispatchStandard(timer.getMessage());
            }
        }
    }

    private static class TimerDetail {
        private final long nsInterval;
        private final String message;
        private long nextInvocation;

        TimerDetail(long currentTime, long nsInterval, String message) {
            this.nsInterval = nsInterval;
            this.message = message;
            this.nextInvocation = currentTime + nsInterval;
        }

        public boolean match(long currentTime) {
            return currentTime >= this.nextInvocation;
        }

        public void advance() {
            this.nextInvocation += this.nsInterval;
        }

        public String getMessage() {
            return this.message;
        }
    }
}
