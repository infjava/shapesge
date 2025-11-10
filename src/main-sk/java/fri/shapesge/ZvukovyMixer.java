package fri.shapesge;

import fri.shapesge.engine.Game;

public class ZvukovyMixer {
    public void zmenHlasitostHudby(int hlasitost) {
        Game.getGame().getGameSoundSystem().setMusicVolume(hlasitost);
    }

    public void zmenHlasitostEfektov(int hlasitost) {
        Game.getGame().getGameSoundSystem().setSoundEffectsVolume(hlasitost);
    }

    public int getHlasitostHudby() {
        return Game.getGame().getGameSoundSystem().getMusicVolume();
    }

    public int getHlasitostEfektov() {
        return Game.getGame().getGameSoundSystem().getSoundEffectsVolume();
    }
}
