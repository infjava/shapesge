package fri.shapesge;

import fri.shapesge.engine.Game;

/**
 * Slúži na ovládanie zvukového mixéra v hre.
 *
 * @author Ján Janech
 * @version 1.0  (10. 11. 2025)
 */
@SuppressWarnings("unused")
public class ZvukovyMixer {
    /**
     * Nastaví hlasitosť pre hudbu.
     * @param hlasitost hlasitosť v rozsahu 0..127
     */
    @SuppressWarnings("unused")
    public void zmenHlasitostHudby(int hlasitost) {
        Game.getGame().getGameSoundSystem().setMusicVolume(hlasitost);
    }

    /**
     * Nastaví hlasitosť pre zvukové efekty.
     * @param hlasitost hlasitosť v rozsahu 0..127
     */
    @SuppressWarnings("unused")
    public void zmenHlasitostEfektov(int hlasitost) {
        Game.getGame().getGameSoundSystem().setSoundEffectsVolume(hlasitost);
    }

    /**
     * Vráti hlasitosť pre hudbu.
     * @return hlasitosť v rozsahu 0..127
     */
    @SuppressWarnings("unused")
    public int getHlasitostHudby() {
        return Game.getGame().getGameSoundSystem().getMusicVolume();
    }

    /**
     * Vráti hlasitosť pre zvukové efekty.
     * @return hlasitosť v rozsahu 0..127
     */
    @SuppressWarnings("unused")
    public int getHlasitostEfektov() {
        return Game.getGame().getGameSoundSystem().getSoundEffectsVolume();
    }
}
