package fri.shapesge.engine.soundsystem;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.SourceDataLine;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public final class GameSoundSystem implements AutoCloseable {
    private static final int SFX_VOICES = 8;

    private final ArrayList<WavEffect> effects;
    private volatile int musicVolume;
    private volatile int effectsVolume;

    private AbstractMusic activeMusic;

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
            return new MidiMusic(file);
        } else if (normalizedPath.endsWith(".wav") || normalizedPath.endsWith(".aiff") || normalizedPath.endsWith(".au")) {
            return new WavMusicStream(file);
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

        var effect = new WavEffect(file, SFX_VOICES);
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


    private static int clamp127(int v) {
        return Math.max(0, Math.min(127, v));
    }

    private abstract class AbstractMusic implements Music {
        private volatile boolean repeating;

        protected AbstractMusic() {
            this.repeating = true;
        }

        @Override
        public final void setRepeating(boolean repeating) {
            this.repeating = repeating;
            this.onRepeatChanged();
        }

        @Override
        public final boolean getRepeating() {
            return this.repeating;
        }

        @Override
        public final synchronized void play() {
            // stop previous
            if (GameSoundSystem.this.activeMusic != null && GameSoundSystem.this.activeMusic != this) {
                GameSoundSystem.this.activeMusic.stop();
            }

            GameSoundSystem.this.activeMusic = this;
            this.startImpl();
            this.applyVolume();
        }

        @Override
        public abstract void stop();

        @Override
        public abstract boolean isPlaying();

        protected abstract void startImpl();

        protected abstract void applyVolume();

        protected void onRepeatChanged() {

        }
    }

    private final class MidiMusic extends AbstractMusic {
        private final File file;
        private Sequencer sequencer;
        private Synthesizer synthesizer;

        MidiMusic(File file) {
            this.file = file;
        }

        @Override
        protected void startImpl() {
            this.stop(); // ensure clean state
            try {
                this.sequencer = MidiSystem.getSequencer(false);
                this.synthesizer = MidiSystem.getSynthesizer();
                this.sequencer.open();
                this.synthesizer.open();
                this.sequencer.getTransmitter().setReceiver(this.synthesizer.getReceiver());
                var sequence = MidiSystem.getSequence(this.file);
                this.sequencer.setSequence(sequence);
                this.sequencer.setLoopCount(this.getRepeating() ? Sequencer.LOOP_CONTINUOUSLY : 0);
                this.sequencer.start();
            } catch (Exception e) {
                this.stop();
                throw new SoundSystemException("MIDI start failed: " + this.file, e);
            }
        }

        @Override
        protected void onRepeatChanged() {
            var currentSequencer = this.sequencer;
            if (currentSequencer != null && currentSequencer.isOpen()) {
                currentSequencer.setLoopCount(this.getRepeating() ? Sequencer.LOOP_CONTINUOUSLY : 0);
            }
        }

        @Override
        protected void applyVolume() {
            var currentSynthesizer = this.synthesizer;
            if (currentSynthesizer == null) {
                return;
            }

            var volume = GameSoundSystem.this.getMusicVolume();
            for (var ch : currentSynthesizer.getChannels()) {
                if (ch != null) {
                    ch.controlChange(7, volume);  // channel volume
                }
            }
        }

        @Override
        public void stop() {
            var currentSequencer = this.sequencer;
            var currentSynthesizer = this.synthesizer;

            this.sequencer = null;
            this.synthesizer = null;

            if (currentSequencer != null) {
                try {
                    currentSequencer.stop();
                    currentSequencer.close();
                } catch (Throwable ignored) {
                    // do nothing here
                }
            }

            if (currentSynthesizer != null) {
                try {
                    currentSynthesizer.close();
                } catch (Throwable ignored) {
                    // do nothing here
                }
            }
            if (GameSoundSystem.this.activeMusic == this) {
                GameSoundSystem.this.activeMusic = null;
            }
        }

        @Override
        public boolean isPlaying() {
            var currentSequencer = this.sequencer;
            return currentSequencer != null && currentSequencer.isOpen() && currentSequencer.isRunning();
        }
    }

    private final class WavMusicStream extends AbstractMusic {
        private final File file;
        private Thread worker;
        private final AtomicBoolean running;
        private volatile SourceDataLine currentLine;
        private volatile AudioFormat pcmFormat;

        WavMusicStream(File file) {
            this.file = file;
            this.running = new AtomicBoolean(false);
        }

        @Override
        protected void startImpl() {
            this.stop();
            this.running.set(true);
            this.worker = new Thread(this::runLoop, "music-wav");
            this.worker.setDaemon(true);
            this.worker.start();
        }

        private void runLoop() {
            while (this.running.get()) {
                try (var src = AudioSystem.getAudioInputStream(this.file)) {
                    var base = src.getFormat();
                    var pcm = this.ensurePcm(base);
                    this.pcmFormat = pcm;

                    try (var ais = AudioSystem.getAudioInputStream(pcm, src)) {
                        DataLine.Info info = new DataLine.Info(SourceDataLine.class, pcm);
                        var line = (SourceDataLine)AudioSystem.getLine(info);
                        this.currentLine = line;
                        line.open(pcm);
                        line.start();
                        this.applyVolume();
                        var buffer = new byte[8192];
                        while (this.running.get()) {
                            var count = ais.read(buffer, 0, buffer.length);

                            if (count < 0) {
                                break;
                            }

                            line.write(buffer, 0, count);
                        }
                        line.drain();
                        line.stop();
                        line.close();
                        this.currentLine = null;

                        if (!this.getRepeating()) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    // stop on failure
                    this.running.set(false);
                }
            }
            if (GameSoundSystem.this.activeMusic == this) {
                GameSoundSystem.this.activeMusic = null;
            }
        }

        @Override
        protected void applyVolume() {
            var line = this.currentLine;
            if (line == null) {
                return;
            }

            if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl c = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
                c.setValue(dbFor127(c, GameSoundSystem.this.getMusicVolume()));
            }
        }

        @Override
        public void stop() {
            this.running.set(false);
            var line = this.currentLine;
            if (line != null) {
                try {
                    line.stop();
                    line.close();
                } catch (Throwable ignored) {
                    // do nothing here
                }
            }
            var workerThread = this.worker;
            this.worker = null;
            if (workerThread != null && workerThread.isAlive()) {
                try {
                    workerThread.join(200);
                } catch (InterruptedException ignored) {

                }
            }
            if (GameSoundSystem.this.activeMusic == this) {
                GameSoundSystem.this.activeMusic = null;
            }
        }

        @Override
        public boolean isPlaying() {
            var workerThread = this.worker;
            return workerThread != null && workerThread.isAlive();
        }

        private AudioFormat ensurePcm(AudioFormat base) {
            if (AudioFormat.Encoding.PCM_SIGNED.equals(base.getEncoding()) && base.getSampleSizeInBits() == 16) {
                return base;
            }

            return new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                base.getSampleRate(),
                16,
                base.getChannels(),
                base.getChannels() * 2,
                base.getSampleRate(),
                false
            );
        }
    }

    private final class WavEffect implements SoundEffect, AutoCloseable {
        private final boolean[] runningFlags;
        private final Clip[] pool;
        private volatile boolean repeating;

        WavEffect(File file, int voices) {
            try (AudioInputStream src = AudioSystem.getAudioInputStream(file)) {
                var base = src.getFormat();
                var pcm = this.ensurePcm(base);

                try (var ais = AudioSystem.getAudioInputStream(pcm, src); var baos = new ByteArrayOutputStream(64 * 1024)) {
                    var buffer = new byte[8192];
                    for (;;) {
                        var count = ais.read(buffer);

                        if (count < 0) {
                            break;
                        }

                        baos.write(buffer, 0, count);
                    }

                    var data = baos.toByteArray();

                    this.pool = new Clip[Math.max(1, voices)];
                    this.runningFlags = new boolean[this.pool.length];

                    DataLine.Info info = new DataLine.Info(Clip.class, pcm);

                    for (var i = 0; i < this.pool.length; i++) {
                        var clip = (Clip)AudioSystem.getLine(info);

                        final var idx = i;

                        clip.addLineListener(ev -> {
                            if (ev.getType() == LineEvent.Type.STOP) {
                                this.runningFlags[idx] = false;
                            }

                            if (ev.getType() == LineEvent.Type.START) {
                                this.runningFlags[idx] = true;
                            }
                        });

                        clip.open(pcm, data, 0, data.length);
                        this.pool[i] = clip;
                    }

                    this.applyVolumeAllVoices();
                }
            } catch (Exception e) {
                throw new SoundSystemException("SFX load failed: " + file, e);
            }

            this.repeating = false;
        }

        @Override
        public void setRepeating(boolean repeating) {
            this.repeating = repeating;
        }

        @Override
        public boolean getRepeating() {
            return this.repeating;
        }

        @Override
        public synchronized void play() {
            var clip = this.acquire();

            if (clip == null) {
                return;
            }

            this.applyVolume(clip);

            if (clip.isRunning()) {
                clip.stop();
            }

            clip.setFramePosition(0);

            if (this.repeating) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                clip.start();
            }
        }

        @Override
        public synchronized void stop() {
            for (var c : this.pool) {
                if (c.isRunning()) {
                    c.stop();
                }
            }
        }

        @Override
        public boolean isPlaying() {
            for (var b : this.runningFlags) {
                if (b) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void close() {
            for (var c : this.pool) {
                try {
                    c.close();
                } catch (Throwable ignored) {
                    // do nothing here
                }
            }
        }

        private Clip acquire() {
            for (var clip : this.pool) {
                if (!clip.isRunning()) {
                    return clip;
                }
            }

            // when no clip is awailable, force the first one
            return this.pool[0];
        }

        void applyVolumeAllVoices() {
            for (var clip : this.pool) {
                this.applyVolume(clip);
            }
        }

        private void applyVolume(Clip c) {
            if (c.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl fc = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
                fc.setValue(dbFor127(fc, GameSoundSystem.this.getSoundEffectsVolume()));
            }
        }

        private AudioFormat ensurePcm(AudioFormat base) {
            if (AudioFormat.Encoding.PCM_SIGNED.equals(base.getEncoding()) && base.getSampleSizeInBits() == 16) {
                return base;
            }

            return new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                base.getSampleRate(),
                16,
                base.getChannels(),
                base.getChannels() * 2,
                base.getSampleRate(),
                false
            );
        }
    }

    private static float dbFor127(FloatControl ctrl, int volume) {
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
