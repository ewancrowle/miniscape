package net.miniscape.minigame.api.state

import lombok.AllArgsConstructor
import net.minikloon.fsmgasm.State
import net.miniscape.minigame.api.Game
import java.time.Duration

@AllArgsConstructor
class PregameState(private val game: Game, private val minPlayers: Int, private val maxPlayers: Int, private val enforceMax: Boolean) : State() {
    override fun onStart() {}
    override fun onEnd() {}
    override fun onUpdate() {}
    override val duration: Duration = Duration.ZERO
}