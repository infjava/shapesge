package fri.shapesge;

import fri.shapesge.engine.Game;

@SuppressWarnings("unused")
public class ZvukovyMixer {
    @SuppressWarnings("unused")
    public void zmenHlasitostHudby(int hlasitost) {
        Game.getGame().getGameSoundSystem().setMusicVolume(hlasitost);
    }

    @SuppressWarnings("unused")
    public void zmenHlasitostEfektov(int hlasitost) {
        Game.getGame().getGameSoundSystem().setSoundEffectsVolume(hlasitost);
    }

    @SuppressWarnings("unused")
    public int getHlasitostHudby() {
        return Game.getGame().getGameSoundSystem().getMusicVolume();
    }

    @SuppressWarnings("unused")
    public int getHlasitostEfektov() {
        return Game.getGame().getGameSoundSystem().getSoundEffectsVolume();
    }
}
