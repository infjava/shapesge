package fri.shapesge.engine.soundsystem;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

class WavMusicStream extends AbstractMusic {
    private final File file;
    private Thread worker;
    private final AtomicBoolean running;
    private volatile SourceDataLine currentLine;
    private volatile AudioFormat pcmFormat;

    WavMusicStream(File file, GameSoundSystem gameSoundSystem) {
        super(gameSoundSystem);
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

        this.getGameSoundSystem().clearActiveMusic(this);
    }

    @Override
    protected void applyVolume() {
        var line = this.currentLine;
        if (line == null) {
            return;
        }

        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl c = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
            c.setValue(GameSoundSystem.dbFor127(c, this.getGameSoundSystem().getMusicVolume()));
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

        this.getGameSoundSystem().clearActiveMusic(this);
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
