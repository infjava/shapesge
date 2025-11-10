package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.MusicHandle;

/**
 * Predstavuje hudbu v hre. Hudba môže byť buď vo formáte MIDI, alebo vo formáte WAVE.
 * Povolené sú len súbory s príponou .mid, .midi, .wav, .aiff a .au.
 *
 * @author Ján Janech
 * @version 1.0  (10. 11. 2025)
 */
@SuppressWarnings("unused")
public class Hudba {
    private final MusicHandle music;

    /**
     * Vytvorí hudbu zo súboru zo zadanou cestou.
     * @param suborSHudbou cesta k súboru s hudbou
     */
    @SuppressWarnings("unused")
    public Hudba(String suborSHudbou) {
        this.music = Game.getGame().getGameSoundSystem().createMusic(suborSHudbou);
    }

    /**
     * Spustí hudbu na pozadí. Naraz môže byť spustená len jedna hudba.
     * Ak už hrá nejaká iná, tá je najskôr zastavená.
     */
    @SuppressWarnings("unused")
    public void spusti() {
        this.music.play();
    }

    /**
     * Zastaví hudbu, ak sa momentálne prehráva.
     */
    @SuppressWarnings("unused")
    public void zastav() {
        this.music.stop();
    }

    /**
     * Zistí, či hudba hrá.
     * @return true, ak hudba hrá.
     */
    @SuppressWarnings("unused")
    public boolean bezi() {
        return this.music.isPlaying();
    }
}
