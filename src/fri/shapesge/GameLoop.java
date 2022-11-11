package fri.shapesge;

class GameLoop implements Runnable {
    private final static long SECOND = 1_000_000_000; // in nanoseconds

    private final int fpsCaps;
    private final GameFPSCounter fpsCounter;
    private final GameWindow gameWindow;
    private final GameTimerProcessor timerProcessor;
    private final GameEventDispatcher eventDispatcher;

    public GameLoop(GameWindow gameWindow, GameTimerProcessor timerProcessor, GameEventDispatcher eventDispatcher, GameFPSCounter fpsCounter, GameConfig gameConfig) {
        this.gameWindow = gameWindow;
        this.timerProcessor = timerProcessor;
        this.eventDispatcher = eventDispatcher;
        this.fpsCounter = fpsCounter;

        this.fpsCaps = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.FPS);
    }

    @Override
    public void run() {
        var frameTimes = this.initialFrameTimes();
        var frameNo = 0;

        for (;;) {
            var currentNanos = System.nanoTime();
            while (frameTimes[frameNo] <= currentNanos) {
                frameTimes[frameNo] = currentNanos + SECOND;
                frameNo = (frameNo + 1) % frameTimes.length;
            }

            var sleepTime = frameTimes[frameNo] - currentNanos;

            try {
                //noinspection BusyWait
                Thread.sleep(sleepTime / 1_000_000, (int) (sleepTime % 1_000_000));
            } catch (InterruptedException e) {
                return;
            }

            this.timerProcessor.processTimers();
            this.eventDispatcher.doEvents();
            this.fpsCounter.countFrame();

            try {
                this.gameWindow.redraw();
            } catch (Exception e) {
                e.printStackTrace();
            }

            frameTimes[frameNo] = currentNanos + SECOND;
            frameNo = (frameNo + 1) % frameTimes.length;
        }
    }

    private long[] initialFrameTimes() {
        var ret = new long[this.fpsCaps];
        var nanosPerFrame = SECOND / this.fpsCaps;
        var currentNanos = System.nanoTime();

        for (int i = 0; i < this.fpsCaps; i++) {
            ret[i] = currentNanos + i * nanosPerFrame;
        }

        return ret;
    }

    public void start() {
        new Thread(this)
                .start();
    }
}
