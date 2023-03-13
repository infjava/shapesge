package fri.shapesge;

class GameLoop implements Runnable {
    private static final long SECOND = 1_000_000_000; // in nanoseconds
    private static final long MILLISECOND = 1_000_000; // in nanoseconds

    private final int fpsCaps;
    private final GameFPSCounter fpsCounter;
    private final GameWindow gameWindow;
    private final GameTimerProcessor timerProcessor;
    private final GameEventDispatcher eventDispatcher;

    GameLoop(GameWindow gameWindow, GameTimerProcessor timerProcessor, GameEventDispatcher eventDispatcher, GameFPSCounter fpsCounter, GameConfig gameConfig) {
        this.gameWindow = gameWindow;
        this.timerProcessor = timerProcessor;
        this.eventDispatcher = eventDispatcher;
        this.fpsCounter = fpsCounter;

        this.fpsCaps = gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.FPS);
    }

    @Override
    public void run() {
        long inaccuracy = 0;

        var lastNanoseconds = System.nanoTime();

        for (;;) {
            this.fpsCounter.countFrame();

            this.timerProcessor.processTimers();
            this.eventDispatcher.doEvents();

            try {
                this.gameWindow.redraw();
            } catch (Exception e) {
                e.printStackTrace();
            }

            var endNanoseconds = System.nanoTime();

            var sleepTime = SECOND / this.fpsCaps - (endNanoseconds - lastNanoseconds) - inaccuracy;
            if (sleepTime < MILLISECOND) {
                sleepTime = MILLISECOND;
            }

            try {
                //noinspection BusyWait
                Thread.sleep(sleepTime / MILLISECOND, (int)(sleepTime % MILLISECOND));
            } catch (InterruptedException e) {
                return;
            }

            lastNanoseconds = System.nanoTime();
            inaccuracy = lastNanoseconds - endNanoseconds - sleepTime;
        }
    }

    public void start() {
        new Thread(this)
                .start();
    }
}
