package fri.shapesge.engine;

class GameFPSCaps {
    private static final long SECOND = 1_000_000_000; // in nanoseconds
    private static final long MILLISECOND = 1_000_000; // in nanoseconds

    private final int fpsCaps;
    private long inaccuracy;
    private long lastNanoseconds;

    GameFPSCaps(int fpsCaps) {
        this.fpsCaps = fpsCaps;
        this.inaccuracy = 0;
        this.lastNanoseconds = System.nanoTime();
    }

    public void doWait() {
        var currentNanoseconds = System.nanoTime();

        var sleepTime = this.getSleepTime(currentNanoseconds);
        try {
            Thread.sleep(sleepTime / MILLISECOND, (int)(sleepTime % MILLISECOND));
        } catch (InterruptedException e) {
            return;
        }

        this.lastNanoseconds = System.nanoTime();
        this.inaccuracy = this.lastNanoseconds - currentNanoseconds - sleepTime;
    }

    private long getSleepTime(long currentNanoseconds) {
        var sleepTime = SECOND / this.fpsCaps - (currentNanoseconds - this.lastNanoseconds) - this.inaccuracy;

        if (sleepTime <= MILLISECOND) {
            return MILLISECOND;
        } else {
            return sleepTime;
        }
    }
}
