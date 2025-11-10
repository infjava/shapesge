package fri.shapesge.engine.soundsystem;

abstract class AbstractMusic implements Music {
    private final GameSoundSystem gameSoundSystem;
    private volatile boolean repeating;

    protected AbstractMusic(GameSoundSystem gameSoundSystem) {
        this.gameSoundSystem = gameSoundSystem;
        this.repeating = true;
    }

    @Override
    public final void setRepeating(boolean repeating) {
        this.repeating = repeating;
        this.onRepeatChanged();
    }

    @Override
    public final boolean getRepeating() {
        return this.repeating;
    }

    @Override
    public final synchronized void play() {
        this.gameSoundSystem.changeActiveMusic(this);

        this.startImpl();
        this.applyVolume();
    }

    @Override
    public abstract void stop();

    @Override
    public abstract boolean isPlaying();

    protected abstract void startImpl();

    protected abstract void applyVolume();

    protected void onRepeatChanged() {

    }

    protected GameSoundSystem getGameSoundSystem() {
        return this.gameSoundSystem;
    }
}
