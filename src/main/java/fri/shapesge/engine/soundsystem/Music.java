package fri.shapesge.engine.soundsystem;

public interface Music {
    void setRepeating(boolean repeating);
    boolean getRepeating();
    void play();
    void stop();
    boolean isPlaying();

    void applyVolume();
}
