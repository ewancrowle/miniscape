package net.miniscape.util.music;

import org.bukkit.Note;
import org.bukkit.Note.Tone;

import javax.sound.midi.ShortMessage;

/**
 * @author authorblues
 */
public class ToneUtil {
    private static final byte BASE_NOTE = new Note(1, Tone.F, true).getId();

    private static final int MIDI_BASE_FSHARP = 54;

    public static double noteToPitch(Note note) {
        double semitones = note.getId() - BASE_NOTE;
        return Math.pow(2.0, semitones / 12.0);
    }

    public static Note midiToNote(ShortMessage smsg) {
        assert smsg.getCommand() == ShortMessage.NOTE_ON;
        int semitones = smsg.getData1() - MIDI_BASE_FSHARP % 12;
        return new Note(semitones % 24);
    }

    public static double midiToPitch(ShortMessage smsg) {
        return noteToPitch(midiToNote(smsg));
    }
}