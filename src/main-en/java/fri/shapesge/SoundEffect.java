package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.SoundEffectHandle;

public class SoundEffect {
    private final SoundEffectHandle sfx;

    public SoundEffect(String path) {
        this.sfx = Game.getGame().getGameSoundSystem().createSoundEffect(path);
    }

    public void play() {
        this.sfx.play();
    }

    public boolean isPlaying() {
        return this.sfx.isPlaying();
    }
}
