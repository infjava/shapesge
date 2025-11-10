package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.SoundEffectHandle;

/**
 * Predstavuje zvukový efekt vo formáte WAVE.
 * Povolené sú len súbory s príponou .wav, .aiff a .au.
 *
 * @author Ján Janech
 * @version 1.0  (10. 11. 2025)
 */
@SuppressWarnings("unused")
public class ZvukovyEfekt {
    private final SoundEffectHandle sfx;

    /**
     * Vytvorí zvukový efekt zo súboru zo zadanou cestou.
     * @param suborSEfektom cesta k súboru s efektom
     */
    @SuppressWarnings("unused")
    public ZvukovyEfekt(String suborSEfektom) {
        this.sfx = Game.getGame().getGameSoundSystem().createSoundEffect(suborSEfektom);
    }

    /**
     * Spustí zvukový efekt.
     */
    @SuppressWarnings("unused")
    public void spusti() {
        this.sfx.play();
    }

    /**
     * Zistí, či zvukový efekt hrá.
     * @return true, ak zvukový efekt hrá.
     */
    @SuppressWarnings("unused")
    public boolean bezi() {
        return this.sfx.isPlaying();
    }
}
