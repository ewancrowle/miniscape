package net.miniscape.util.music;

import com.google.common.collect.Maps;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.Receiver;
import javax.sound.midi.ShortMessage;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * Midi Receiver for processing note events.
 *
 * @author authorblues
 */
public class NoteBlockReceiver implements Receiver {
    private static final float VOLUME_RANGE = 10.0f;

    private final Set<Player> listeners;
    private final Map<Integer, Integer> channelPatches;
    private Location location;

    public NoteBlockReceiver(Set<Player> listeners) throws InvalidMidiDataException, IOException {
        this.listeners = listeners;
        this.channelPatches = Maps.newHashMap();
    }

    public NoteBlockReceiver(Set<Player> listeners, Location location) throws InvalidMidiDataException, IOException {
        this.listeners = listeners;
        this.channelPatches = Maps.newHashMap();
        this.location = location;
    }

    @Override
    public void send(MidiMessage m, long time) {
        if (m instanceof ShortMessage) {
            ShortMessage smessage = (ShortMessage) m;
            int chan = smessage.getChannel();

            switch (smessage.getCommand()) {
                case ShortMessage.PROGRAM_CHANGE:
                    int patch = smessage.getData1();
                    channelPatches.put(chan, patch);
                    break;

                case ShortMessage.NOTE_ON:
                    this.playNote(smessage);
                    break;

                case ShortMessage.NOTE_OFF:
                    break;
            }
        }
    }

    public void playNote(ShortMessage message) {
        if (ShortMessage.NOTE_ON != message.getCommand()) {
            return;
        }

        float pitch = (float) ToneUtil.midiToPitch(message);
        float volume = VOLUME_RANGE * (message.getData2() / 127.0f);

        Integer patch = channelPatches.get(message.getChannel());
        Sound instrument = Sound.NOTE_PIANO;
        if (patch != null) {
            instrument = MidiUtil.patchToInstrument(patch);
        }

        for (Player player : listeners) {
            if (location != null) {
                player.playSound(location, instrument, volume, pitch);
            } else {
                player.playSound(player.getLocation(), instrument, volume, pitch);
            }
        }
    }

    @Override
    public void close() {
        listeners.clear();
        channelPatches.clear();
    }
}