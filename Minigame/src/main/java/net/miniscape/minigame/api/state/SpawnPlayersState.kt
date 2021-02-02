package net.miniscape.minigame.api.state

import net.minikloon.fsmgasm.State
import net.miniscape.minigame.api.Game
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.time.Duration

class SpawnPlayersState(private val game: Game) : State() {
    override fun onStart() {
        for (player: Player in Bukkit.getOnlinePlayers()) {
            player.teleport(Bukkit.getWorld(game.properties.map).spawnLocation)
        }
    }

    override fun onUpdate() {}
    override fun onEnd() {}
    override val duration: Duration = Duration.ZERO
}