package fri.shapesge;

import fri.shapesge.engine.Game;

/**
 * Used to control the game sound mixer.
 *
 * @author Ján Janech
 * @version 1.0  (10. 11. 2025)
 */
@SuppressWarnings("unused")
public class SoundMixer {
    /**
     * Sets the volume for music.
     * @param volume volume in the range 0..127
     */
    @SuppressWarnings("unused")
    public void changeMusicVolume(int volume) {
        Game.getGame().getGameSoundSystem().setMusicVolume(volume);
    }

    /**
     * Sets the volume for sound effects.
     * @param volume volume in the range 0..127
     */
    @SuppressWarnings("unused")
    public void changeSoundEffectsVolume(int volume) {
        Game.getGame().getGameSoundSystem().setSoundEffectsVolume(volume);
    }

    /**
     * Returns the volume for music.
     * @return volume in the range 0..127
     */
    @SuppressWarnings("unused")
    public int getMusicVolume() {
        return Game.getGame().getGameSoundSystem().getMusicVolume();
    }

    /**
     * Returns the volume for sound effects.
     * @return volume in the range 0..127
     */
    @SuppressWarnings("unused")
    public int getSoundEffectsVolume() {
        return Game.getGame().getGameSoundSystem().getSoundEffectsVolume();
    }
}
