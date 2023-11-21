package fri.shapesge.engine;

class GameFPSCounter {
    private static final long SECOND = 1_000_000_000; // in ns
    private long lastSecondNanotime;
    private int fps;
    private int fpsCounter;

    GameFPSCounter() {
        this.lastSecondNanotime = 0;
        this.fps = 0;
        this.fpsCounter = 0;
    }

    public void countFrame() {
        var currentTime = System.nanoTime();
        if (currentTime - this.lastSecondNanotime > SECOND) {
            this.fps = this.fpsCounter;
            this.fpsCounter = 0;
            this.lastSecondNanotime = currentTime;
        }
        this.fpsCounter++;
    }

    public int getFPS() {
        return this.fps;
    }
}
