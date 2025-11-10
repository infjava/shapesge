package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.MusicHandle;

public class Hudba {
    private final MusicHandle music;

    public Hudba(String cesta) {
        this.music = Game.getGame().getGameSoundSystem().createMusic(cesta);
    }

    public void spusti() {
        this.music.play();
    }

    public void zastav() {
        this.music.stop();
    }

    public boolean bezi() {
        return this.music.isPlaying();
    }
}
