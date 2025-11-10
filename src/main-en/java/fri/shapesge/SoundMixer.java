package fri.shapesge;

import fri.shapesge.engine.Game;

@SuppressWarnings("unused")
public class SoundMixer {
    @SuppressWarnings("unused")
    public void changeMusicVolume(int hlasitost) {
        Game.getGame().getGameSoundSystem().setMusicVolume(hlasitost);
    }

    @SuppressWarnings("unused")
    public void changeSoundEffectsVolume(int hlasitost) {
        Game.getGame().getGameSoundSystem().setSoundEffectsVolume(hlasitost);
    }

    @SuppressWarnings("unused")
    public int getMusicVolume() {
        return Game.getGame().getGameSoundSystem().getMusicVolume();
    }

    @SuppressWarnings("unused")
    public int getSoundEffectsVolume() {
        return Game.getGame().getGameSoundSystem().getSoundEffectsVolume();
    }
}
