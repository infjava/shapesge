package fri.shapesge.engine.soundsystem;

import fri.shapesge.engine.GameParser;
import fri.shapesge.engine.ShapesGEException;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;

class MidiMusic implements MusicHandle {
    private final GameParser gameParser;
    private final String path;
    private final GameSoundSystem gameSoundSystem;

    private volatile boolean repeating;

    private Sequencer sequencer;
    private Synthesizer synthesizer;

    MidiMusic(GameParser gameParser, String path, GameSoundSystem gameSoundSystem) {
        this.gameParser = gameParser;
        this.path = path;
        this.gameSoundSystem = gameSoundSystem;
        this.repeating = true;
    }

    @Override
    public final void setRepeating(boolean repeating) {
        this.repeating = repeating;

        var currentSequencer = this.sequencer;
        if (currentSequencer != null && currentSequencer.isOpen()) {
            currentSequencer.setLoopCount(this.getRepeating() ? Sequencer.LOOP_CONTINUOUSLY : 0);
        }
    }

    @Override
    public final boolean getRepeating() {
        return this.repeating;
    }

    @Override
    public final synchronized void play() {
        this.gameSoundSystem.changeActiveMusic(this);

        this.stop(); // ensure clean state

        try {
            this.sequencer = MidiSystem.getSequencer(false);
            this.synthesizer = MidiSystem.getSynthesizer();
            this.sequencer.open();
            this.synthesizer.open();
            this.sequencer.getTransmitter().setReceiver(this.synthesizer.getReceiver());
            var sequence = this.gameParser.parseMidiAudio(this.path);
            this.sequencer.setSequence(sequence);
            this.sequencer.setLoopCount(this.getRepeating() ? Sequencer.LOOP_CONTINUOUSLY : 0);
            this.sequencer.start();
        } catch (Exception e) {
            this.stop();
            throw new ShapesGEException("MIDI start failed: " + this.path, e);
        }

        this.applyVolume();
    }

    @Override
    public void applyVolume() {
        var currentSynthesizer = this.synthesizer;
        if (currentSynthesizer == null) {
            return;
        }

        var volume = this.gameSoundSystem.getMusicVolume();
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

        this.gameSoundSystem.clearActiveMusic(this);
    }

    @Override
    public boolean isPlaying() {
        var currentSequencer = this.sequencer;
        return currentSequencer != null && currentSequencer.isOpen() && currentSequencer.isRunning();
    }
}
