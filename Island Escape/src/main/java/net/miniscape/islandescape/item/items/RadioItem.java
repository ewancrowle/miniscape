package net.miniscape.islandescape.item.items;

import lombok.Getter;
import lombok.Setter;
import net.miniscape.islandescape.item.BaseItem;
import net.miniscape.islandescape.item.GameItem;
import net.miniscape.islandescape.item.furniture.FurnitureItem;
import net.miniscape.util.event.TickEvent;
import net.miniscape.util.music.MidiUtil;
import net.miniscape.util.music.NoteBlockReceiver;
import net.miniscape.util.music.Song;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;

@Getter
@Setter
public class RadioItem extends FurnitureItem {
    private Sequencer sequencer;

    public RadioItem() {
        super(GameItem.RADIO, new ItemStack(Material.JUKEBOX), 3000, 2400);
    }

    @Override
    public void onInteract(Player player) {
        togglePlaying(Song.BUBBLEGUM_KK);
    }

    @Override
    public void onDestruct(Player player) {
        super.onDestruct(player);
    }

    @Override
    public void placeItem(Location location) {

    }

    @Override
    public void removeItem(Location location) {
        // ... remove from environment and destroy
        getPhysicalList().clear();
    }

    public void togglePlaying(Song song) {
        if (sequencer.isRunning()) {
            sequencer.stop();
            return;
        }

        Optional<File> fileOptional = song.getDefaultFile();
        try {
            if (sequencer == null) {
                sequencer = MidiUtil.playMidi(fileOptional.get(), 1.0f, new HashSet<>(Bukkit.getOnlinePlayers()), getLocation());
            } else {
                sequencer.setMicrosecondPosition(0);
                sequencer.start();
            }
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void onUpdate(TickEvent event) {
        if (!sequencer.isRunning()) {
            sequencer.setMicrosecondPosition(0);
            sequencer.start();
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {
            NoteBlockReceiver noteblockRecv = new NoteBlockReceiver(new HashSet<>(Bukkit.getOnlinePlayers()), getLocation());
            sequencer.getTransmitter().setReceiver(noteblockRecv);
        } catch (InvalidMidiDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MidiUnavailableException e) {
            e.printStackTrace();
        }
    }
}