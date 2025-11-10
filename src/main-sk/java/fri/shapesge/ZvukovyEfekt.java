package fri.shapesge;

import fri.shapesge.engine.Game;
import fri.shapesge.engine.soundsystem.SoundEffectHandle;

public class ZvukovyEfekt {
    private final SoundEffectHandle sfx;

    public ZvukovyEfekt(String cesta) {
        this.sfx = Game.getGame().getGameSoundSystem().createSoundEffect(cesta);
    }

    public void spusti() {
        this.sfx.play();
    }

    public boolean bezi() {
        return this.sfx.isPlaying();
    }
}
