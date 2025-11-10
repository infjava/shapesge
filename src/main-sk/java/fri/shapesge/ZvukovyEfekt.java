package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.SoundEffectHandle;

@SuppressWarnings("unused")
public class ZvukovyEfekt {
    private final SoundEffectHandle sfx;

    @SuppressWarnings("unused")
    public ZvukovyEfekt(String cesta) {
        this.sfx = Game.getGame().getGameSoundSystem().createSoundEffect(cesta);
    }

    @SuppressWarnings("unused")
    public void spusti() {
        this.sfx.play();
    }

    @SuppressWarnings("unused")
    public boolean bezi() {
        return this.sfx.isPlaying();
    }
}
