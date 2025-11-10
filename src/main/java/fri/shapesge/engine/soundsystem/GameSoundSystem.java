package fri.shapesge.engine.soundsystem;

import javax.sound.sampled.FloatControl;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public final class GameSoundSystem implements AutoCloseable {
    private static final int SFX_VOICES = 8;

    private final ArrayList<WavEffect> effects;
    private volatile int musicVolume;
    private volatile int effectsVolume;

    private Music activeMusic;

    public GameSoundSystem() {
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
        Objects.requireNonNull(path, "path");

        var file = new File(path);
        if (!file.isFile()) {
            throw new SoundSystemException("file not found: " + path);
        }

        var normalizedPath = path.toLowerCase(Locale.ROOT);

        if (normalizedPath.endsWith(".mid") || normalizedPath.endsWith(".midi")) {
            return new MidiMusic(file, this);
        } else if (normalizedPath.endsWith(".wav") || normalizedPath.endsWith(".aiff") || normalizedPath.endsWith(".au")) {
            return new WavMusicStream(file, this);
        } else {
            throw new SoundSystemException("unsupported music format: " + path);
        }
    }

    public synchronized SoundEffect createSoundEffect(String path) {
        Objects.requireNonNull(path, "path");

        var file = new File(path);
        if (!file.isFile()) {
            throw new SoundSystemException("file not found: " + path);
        }

        var normalizedPath = path.toLowerCase(Locale.ROOT);

        if (!(normalizedPath.endsWith(".wav") || normalizedPath.endsWith(".aiff") || normalizedPath.endsWith(".au"))) {
            throw new SoundSystemException("unsupported SFX format (use WAV/AIFF/AU): " + path);
        }

        var effect = new WavEffect(file, SFX_VOICES, this);
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

    static float dbFor127(FloatControl ctrl, int volume) {
        if (volume <= 0) {
            return ctrl.getMinimum();
        }

        var lin = volume / 127f;                 // 0..1
        var db = (float)(20.0 * Math.log10(lin)); // 0 -> -inf, 1 -> 0 dB
        db = Math.max(ctrl.getMinimum(), Math.min(0f, db));
        db = Math.max(ctrl.getMinimum(), Math.min(ctrl.getMaximum(), db));

        return db;
    }
}
