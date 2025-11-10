package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.SoundEffectHandle;

/**
 * Represents a sound effect in WAVE format.
 * Only files with the extension .wav, .aiff and .au are allowed.
 *
 * @author Ján Janech
 * @version 1.0  (10. 11. 2025)
 */
@SuppressWarnings("unused")
public class SoundEffect {
    private final SoundEffectHandle sfx;

    /**
     * Creates a sound effect from a file with the specified path.
     * @param effectPath path to the effect file
     */
    @SuppressWarnings("unused")
    public SoundEffect(String effectPath) {
        this.sfx = Game.getGame().getGameSoundSystem().createSoundEffect(effectPath);
    }

    /**
     * Starts a sound effect.
     */
    @SuppressWarnings("unused")
    public void play() {
        this.sfx.play();
    }

    /**
     * Determines whether a sound effect is playing.
     * @return true if a sound effect is playing.
     */
    @SuppressWarnings("unused")
    public boolean isPlaying() {
        return this.sfx.isPlaying();
    }
}
