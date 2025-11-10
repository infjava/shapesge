package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.MusicHandle;

@SuppressWarnings("unused")
public class Hudba {
    private final MusicHandle music;

    @SuppressWarnings("unused")
    public Hudba(String cesta) {
        this.music = Game.getGame().getGameSoundSystem().createMusic(cesta);
    }

    @SuppressWarnings("unused")
    public void spusti() {
        this.music.play();
    }

    @SuppressWarnings("unused")
    public void zastav() {
        this.music.stop();
    }

    @SuppressWarnings("unused")
    public boolean bezi() {
        return this.music.isPlaying();
    }
}
