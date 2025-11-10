package fri.shapesge.engine.soundsystem;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;

class WavEffect implements SoundEffect, AutoCloseable {
    private final boolean[] runningFlags;
    private final Clip[] pool;
    private final GameSoundSystem gameSoundSystem;
    private volatile boolean repeating;

    WavEffect(File file, int voices, GameSoundSystem gameSoundSystem) {
        this.gameSoundSystem = gameSoundSystem;
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

    public void applyVolumeAllVoices() {
        for (var clip : this.pool) {
            this.applyVolume(clip);
        }
    }

    private void applyVolume(Clip c) {
        if (c.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl fc = (FloatControl)c.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(GameSoundSystem.dbFor127(fc, this.gameSoundSystem.getSoundEffectsVolume()));
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
