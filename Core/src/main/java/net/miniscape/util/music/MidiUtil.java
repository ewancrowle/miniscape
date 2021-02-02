package net.miniscape.util.music;

import com.google.inject.internal.cglib.core.$Local;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.sound.midi.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

/**
 * @author authorblues & Ewan Crowle
 */
public class MidiUtil {
    private static final int[] instruments = {
            0, 0, 0, 0, 0, 0, 0, 0, // 1-8 Piano
            0, 0, 0, 0, 0, 0, 0, 0, // 9-16 Chromatic Percussion
            0, 0, 0, 0, 0, 0, 0, 0, // 17-24 Organ
            1, 1, 1, 1, 1, 1, 1, 1, // 25-32 Guitar
            3, 3, 3, 2, 2, 2, 2, 2, // 33-40 Bass
            1, 1, 1, 1, 1, 1, 1, 1, // 41-48 Strings
            1, 1, 1, 1, 1, 1, 1, 6, // 49-56 Ensemble
            0, 0, 0, 0, 0, 0, 0, 0, // 57-64 Brass
            0, 0, 0, 0, 0, 0, 0, 0, // 65-72 Reed
            0, 0, 0, 0, 0, 0, 0, 0, // 73-80 Pipe
            0, 0, 0, 0, 0, 0, 0, 0, // 81-88 Synth Lead
            0, 0, 0, 0, 0, 0, 0, 0, // 89-96 Synth Pad
            0, 0, 0, 0, 0, 0, 0, 0, // 97-104 Synth Effects
            1, 1, 1, 1, 1, 1, 1, 1, // 105-112 Ethnic
            1, 1, 1, 6, 4, 4, 4, 4, // 113-120 Percussive
            3, 1, 1, 1, 1, 5, 5, 6, // 121-128 Sound Effects
    };

    public static Sound patchToInstrument(int patch) {
        switch (instruments[patch]) {
            case 0:
                return Sound.NOTE_PIANO;
            case 1:
                return Sound.NOTE_PLING;
            case 2:
                return Sound.NOTE_BASS;
            case 3:
                return Sound.NOTE_BASS_GUITAR;
            case 4:
                return Sound.NOTE_BASS_DRUM;
            case 5:
                return Sound.NOTE_SNARE_DRUM;
            case 6:
                return Sound.NOTE_STICKS;
        }

        return Sound.NOTE_PIANO;
    }

    public static Sequencer playMidi(Sequence seq, float tempo, Set<Player> listeners) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        Sequencer sequencer = MidiSystem.getSequencer(false);
        sequencer.setSequence(seq);
        sequencer.open();

        sequencer.setTempoFactor(tempo);

        NoteBlockReceiver noteblockRecv = new NoteBlockReceiver(listeners);
        sequencer.getTransmitter().setReceiver(noteblockRecv);
        sequencer.start();

        return sequencer;
    }

    public static Sequencer playMidi(Sequence seq, float tempo, Set<Player> listeners, Location location) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        Sequencer sequencer = MidiSystem.getSequencer(false);
        sequencer.setSequence(seq);
        sequencer.open();

        sequencer.setTempoFactor(tempo);

        NoteBlockReceiver noteblockRecv = new NoteBlockReceiver(listeners, location);
        sequencer.getTransmitter().setReceiver(noteblockRecv);
        sequencer.start();

        return sequencer;
    }

    public static Sequencer playMidi(File file, float tempo, Set<Player> listeners, Location location) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        return playMidi(MidiSystem.getSequence(file), tempo, listeners, location);
    }

    public static Sequencer playMidi(File file, float tempo, Set<Player> listeners) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        return playMidi(MidiSystem.getSequence(file), tempo, listeners);
    }

    public static Sequencer playMidi(InputStream stream, float tempo, Set<Player> listeners) throws InvalidMidiDataException, IOException, MidiUnavailableException {
        return playMidi(MidiSystem.getSequence(stream), tempo, listeners);
    }

    public static boolean playMidiQuietly(File file, float tempo, Set<Player> listeners) {
        try {
            MidiUtil.playMidi(file, tempo, listeners);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean playMidiQuietly(InputStream stream, float tempo, Set<Player> listeners) {
        try {
            MidiUtil.playMidi(stream, tempo, listeners);
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
            return false;
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static boolean playMidiQuietly(File file, Set<Player> listeners) {
        return playMidiQuietly(file, 1.0f, listeners);
    }

    public static boolean playMidiQuietly(InputStream stream, Set<Player> listeners) {
        return playMidiQuietly(stream, 1.0f, listeners);
    }
}