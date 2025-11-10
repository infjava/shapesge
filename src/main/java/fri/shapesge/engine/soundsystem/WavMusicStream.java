package fri.shapesge.engine.soundsystem;

import fri.shapesge.engine.GameParser;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.util.concurrent.atomic.AtomicBoolean;

class WavMusicStream implements MusicHandle {
    private final GameParser gameParser;
    private final String path;
    private final GameSoundSystem gameSoundSystem;

    private volatile boolean repeating;

    private Thread worker;
    private final AtomicBoolean running;
    private volatile SourceDataLine currentLine;

    WavMusicStream(GameParser gameParser, String path, GameSoundSystem gameSoundSystem) {
        this.gameParser = gameParser;
        this.path = path;
        this.gameSoundSystem = gameSoundSystem;
        this.repeating = true;
        this.running = new AtomicBoolean(false);
    }

    @Override
    public final void setRepeating(boolean repeating) {
        this.repeating = repeating;
    }

    @Override
    public final boolean getRepeating() {
        return this.repeating;
    }


    @Override
    public final synchronized void play() {
        this.gameSoundSystem.changeActiveMusic(this);

        this.stop();
        this.running.set(true);
        this.worker = new Thread(this::runLoop, "music-wav");
        this.worker.setDaemon(true);
        this.worker.start();

        this.applyVolume();
    }

    private void runLoop() {
        while (this.running.get()) {
            try (var src = this.gameParser.parseWaveAudio(this.path)) {
                var base = src.getFormat();
                var pcm = WavUtils.ensurePcm(base);

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

        this.gameSoundSystem.clearActiveMusic(this);
    }

    @Override
    public void applyVolume() {
        var line = this.currentLine;
        if (line == null) {
            return;
        }

        WavUtils.applyVolume(line, this.gameSoundSystem.getMusicVolume());
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

        this.gameSoundSystem.clearActiveMusic(this);
    }

    @Override
    public boolean isPlaying() {
        var workerThread = this.worker;
        return workerThread != null && workerThread.isAlive();
    }
}
