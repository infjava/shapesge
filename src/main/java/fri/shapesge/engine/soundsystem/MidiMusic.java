package fri.shapesge.engine.soundsystem;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import java.io.File;

class MidiMusic extends AbstractMusic {
    private final File file;
    private Sequencer sequencer;
    private Synthesizer synthesizer;

    MidiMusic(File file, GameSoundSystem gameSoundSystem) {
        super(gameSoundSystem);
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

        var volume = this.getGameSoundSystem().getMusicVolume();
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

        this.getGameSoundSystem().clearActiveMusic(this);
    }

    @Override
    public boolean isPlaying() {
        var currentSequencer = this.sequencer;
        return currentSequencer != null && currentSequencer.isOpen() && currentSequencer.isRunning();
    }
}
