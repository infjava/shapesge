package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.MusicHandle;

/**
 * Represents the music in the game. The music can be in either MIDI or WAVE format.
 * Only files with the extension .mid, .midi, .wav, .aiff and .au are allowed.
 *
 * @author Ján Janech
 * @version 1.0  (10. 11. 2025)
 */
@SuppressWarnings("unused")
public class Music {
    private final MusicHandle music;

    /**
     * Creates music from a file with the specified path.
     * @param musicPath path to the music file
     */
    @SuppressWarnings("unused")
    public Music(String musicPath) {
        this.music = Game.getGame().getGameSoundSystem().createMusic(musicPath);
    }

    /**
     * Plays background music. Only one music can be played at a time.
     * If another music is already playing, it is stopped first.
     */
    @SuppressWarnings("unused")
    public void play() {
        this.music.play();
    }

    /**
     * Stops music if it is currently playing.
     */
    @SuppressWarnings("unused")
    public void stop() {
        this.music.stop();
    }

    /**
     * Detects whether music is playing.
     * @return true if music is playing.
     */
    @SuppressWarnings("unused")
    public boolean isPlaying() {
        return this.music.isPlaying();
    }
}
