package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.MusicHandle;

@SuppressWarnings("unused")
public class Music {
    private final MusicHandle music;

    @SuppressWarnings("unused")
    public Music(String path) {
        this.music = Game.getGame().getGameSoundSystem().createMusic(path);
    }

    @SuppressWarnings("unused")
    public void play() {
        this.music.play();
    }

    @SuppressWarnings("unused")
    public void stop() {
        this.music.stop();
    }

    @SuppressWarnings("unused")
    public boolean isPlaying() {
        return this.music.isPlaying();
    }
}
