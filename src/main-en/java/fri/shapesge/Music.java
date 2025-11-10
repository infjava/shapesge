package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.MusicHandle;

public class Music {
    private final MusicHandle music;

    public Music(String path) {
        this.music = Game.getGame().getGameSoundSystem().createMusic(path);
    }

    public void play() {
        this.music.play();
    }

    public void stop() {
        this.music.stop();
    }

    public boolean isPlaying() {
        return this.music.isPlaying();
    }
}
