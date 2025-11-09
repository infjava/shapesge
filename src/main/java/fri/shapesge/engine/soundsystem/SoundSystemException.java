package fri.shapesge.engine.soundsystem;

public class SoundSystemException extends RuntimeException {
    public SoundSystemException(String msg) {
        super(msg);
    }

    public SoundSystemException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
