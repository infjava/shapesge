package fri.shapesge.engine.soundsystem;

import fri.shapesge.engine.GameParser;
import fri.shapesge.engine.ShapesGEException;

import java.util.ArrayList;
import java.util.Locale;

public final class GameSoundSystem implements AutoCloseable {
    private static final int SFX_VOICES = 8;

    private final ArrayList<WavEffect> effects;
    private final GameParser gameParser;
    private volatile int musicVolume;
    private volatile int effectsVolume;

    private Music activeMusic;

    public GameSoundSystem(GameParser gameParser) {
        this.gameParser = gameParser;
        this.effects = new ArrayList<>();
        this.musicVolume = 127;
        this.effectsVolume = 127;
    }

    // 0..127
    public synchronized void setMusicVolume(int v) {
        this.musicVolume = clamp127(v);
        if (this.activeMusic != null) {
            this.activeMusic.applyVolume();
        }
    }

    public synchronized int getMusicVolume() {
        return this.musicVolume;
    }

    public synchronized void setSoundEffectsVolume(int v) {
        this.effectsVolume = clamp127(v);
        for (var effect : this.effects) {
            effect.applyVolumeAllVoices();
        }
    }

    public synchronized int getSoundEffectsVolume() {
        return this.effectsVolume;
    }

    public synchronized Music createMusic(String path) {
        var normalizedPath = path.toLowerCase(Locale.ROOT);

        if (normalizedPath.endsWith(".mid") || normalizedPath.endsWith(".midi")) {
            return new MidiMusic(this.gameParser, path, this);
        } else if (normalizedPath.endsWith(".wav") || normalizedPath.endsWith(".aiff") || normalizedPath.endsWith(".au")) {
            return new WavMusicStream(this.gameParser, path, this);
        } else {
            throw new ShapesGEException("unsupported music format: " + path);
        }
    }

    public synchronized SoundEffect createSoundEffect(String path) {
        var normalizedPath = path.toLowerCase(Locale.ROOT);

        if (!(normalizedPath.endsWith(".wav") || normalizedPath.endsWith(".aiff") || normalizedPath.endsWith(".au"))) {
            throw new ShapesGEException("unsupported SFX format (use WAV/AIFF/AU): " + path);
        }

        var effect = new WavEffect(this.gameParser, path, SFX_VOICES, this);
        this.effects.add(effect);
        return effect;
    }

    @Override
    public synchronized void close() {
        if (this.activeMusic != null) {
            this.activeMusic.stop();
            this.activeMusic = null;
        }
        for (var effect : this.effects) {
            try {
                effect.close();
            } catch (Throwable ignored) {
                // do nothing here
            }
        }

        this.effects.clear();
    }

    void changeActiveMusic(Music music) {
        if (this.activeMusic != null && this.activeMusic != music) {
            this.activeMusic.stop();
        }

        this.activeMusic = music;
    }

    void clearActiveMusic(Music music) {
        if (this.activeMusic != music) {
            return;
        }

        this.activeMusic = null;
    }

    static int clamp127(int v) {
        return Math.max(0, Math.min(127, v));
    }
}
