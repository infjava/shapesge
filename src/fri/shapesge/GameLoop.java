package fri.shapesge;

class GameLoop implements Runnable {
    private final static long SECOND = 1_000_000_000; // in nanoseconds

    private final int fpsCaps;
    private final GameWindow gameWindow;
    private final GameTimerProcessor timerProcessor;
    private final GameEventDispatcher eventDispatcher;
    private int fps;

    public GameLoop(GameWindow gameWindow, GameTimerProcessor timerProcessor, GameEventDispatcher eventDispatcher, GameConfig gameConfig) {
        this.gameWindow = gameWindow;
        this.timerProcessor = timerProcessor;
        this.eventDispatcher = eventDispatcher;

        this.fpsCaps = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.FPS);
        this.fps = 0;
    }

    @Override
    public void run() {
        var frameTimes = this.initialFrameTimes();
        var frameNo = 0;
        var fpsCounter = 0;

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

            try {
                this.gameWindow.redraw();
            } catch (Exception e) {
                e.printStackTrace();
            }

            frameTimes[frameNo] = currentNanos + SECOND;
            frameNo = (frameNo + 1) % frameTimes.length;

            fpsCounter++;
            if (frameNo == 0) {
                this.fps = fpsCounter;
                this.gameWindow.notifyFPS(fpsCounter);
                fpsCounter = 0;
            }
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
