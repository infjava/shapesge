package fri.shapesge.engine.soundsystem;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.Line;

final class WavUtils {
    private WavUtils() { }

    static AudioFormat ensurePcm(AudioFormat base) {
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

    static void applyVolume(Line line, int volume) {
        if (line.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl fc = (FloatControl)line.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(dbFor127(fc, volume));
        }
    }
}
