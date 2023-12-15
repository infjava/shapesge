package fri.shapesge.engine;

class GameLoop implements Runnable {
    private final GameFPSCaps fpsCaps;
    private final GameFPSCounter fpsCounter;
    private final GameWindow gameWindow;
    private final GameTimerProcessor timerProcessor;
    private final GameEventDispatcher eventDispatcher;

    GameLoop(GameWindow gameWindow, GameTimerProcessor timerProcessor, GameEventDispatcher eventDispatcher, GameFPSCounter fpsCounter, GameConfig gameConfig) {
        this.gameWindow = gameWindow;
        this.timerProcessor = timerProcessor;
        this.eventDispatcher = eventDispatcher;
        this.fpsCounter = fpsCounter;

        this.fpsCaps = new GameFPSCaps(gameConfig.getInt(GameConfig.WINDOW_SECTION, GameConfig.FPS));
    }

    @Override
    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        for (;;) {
            this.fpsCounter.countFrame();

            this.timerProcessor.processTimers();
            this.eventDispatcher.doEvents();

            try {
                this.gameWindow.redraw();
            } catch (Exception e) {
                //noinspection CallToPrintStackTrace
                e.printStackTrace();
            }

            this.fpsCaps.doWait();
        }
    }

    public void start() {
        new Thread(this)
                .start();
    }
}
