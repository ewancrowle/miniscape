package net.miniscape.minigame.api.state

import net.minikloon.fsmgasm.State
import net.miniscape.minigame.api.Game
import java.time.Duration

class EndgameState(private val game: Game) : State() {
    override fun onStart() {}
    override fun onEnd() {}
    override fun onUpdate() {}
    override val duration: Duration = Duration.ZERO
}