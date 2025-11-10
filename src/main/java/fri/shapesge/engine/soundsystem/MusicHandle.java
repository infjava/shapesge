package fri.shapesge.engine.soundsystem;

public interface MusicHandle {
    void setRepeating(boolean repeating);
    boolean getRepeating();
    void play();
    void stop();
    boolean isPlaying();

    void applyVolume();
}
