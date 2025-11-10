package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.SoundEffectHandle;

@SuppressWarnings("unused")
public class SoundEffect {
    private final SoundEffectHandle sfx;

    @SuppressWarnings("unused")
    public SoundEffect(String path) {
        this.sfx = Game.getGame().getGameSoundSystem().createSoundEffect(path);
    }

    @SuppressWarnings("unused")
    public void play() {
        this.sfx.play();
    }

    @SuppressWarnings("unused")
    public boolean isPlaying() {
        return this.sfx.isPlaying();
    }
}
