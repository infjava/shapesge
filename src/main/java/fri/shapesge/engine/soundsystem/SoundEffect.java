package fri.shapesge.engine.soundsystem;

public interface SoundEffect {
    void setRepeating(boolean repeating);
    boolean getRepeating();
    void play();
    void stop();
    boolean isPlaying();
}
