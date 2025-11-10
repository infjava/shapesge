package fri.shapesge;

import fri.shapesge.engine.Game;

public class SoundMixer {
    public void changeMusicVolume(int hlasitost) {
        Game.getGame().getGameSoundSystem().setMusicVolume(hlasitost);
    }

    public void changeSoundEffectsVolume(int hlasitost) {
        Game.getGame().getGameSoundSystem().setSoundEffectsVolume(hlasitost);
    }

    public int getMusicVolume() {
        return Game.getGame().getGameSoundSystem().getMusicVolume();
    }

    public int getSoundEffectsVolume() {
        return Game.getGame().getGameSoundSystem().getSoundEffectsVolume();
    }
}
