package fri.shapesge;

import java.util.ArrayList;


class GameTimerProcessor {
    private static final long MILLISECONDS = 1_000_000; // in nanoseconds
    private final GameEventDispatcher eventDispatcher;
    private final ArrayList<TimerDetail> timers;

    public GameTimerProcessor(GameEventDispatcher eventDispatcher, GameConfig gameConfig) {
        this.eventDispatcher = eventDispatcher;
        this.timers = new ArrayList<>();

        var currentTime = System.nanoTime();
        for (String message : gameConfig.getOptions(GameConfig.TIMER_SECTION)) {
            var msInterval = gameConfig.getInt(GameConfig.TIMER_SECTION, message);
            this.registerTimer(currentTime, msInterval, message);
        }
    }

    private void registerTimer(long currentTime, int msInterval, String message) {
        this.timers.add(new TimerDetail(currentTime, msInterval * MILLISECONDS, message));
    }

    public synchronized void processTimers() {
        var currentTime = System.nanoTime();

        for (var timer : this.timers) {
            if (timer.match(currentTime)) {
                timer.advance();
                this.eventDispatcher.dispatchStandard(timer.getMessage());
            }
        }
    }

    private static class TimerDetail {
        private final long nsInterval;
        private final String message;
        private long nextInvocation;

        public TimerDetail(long currentTime, long nsInterval, String message) {
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
