package net.miniscape.minigame.api.state

import net.minikloon.fsmgasm.State
import java.time.Duration

class RunningState(private val maxDurationMinutes: Int) : State() {
    override fun onStart() {}
    override fun onUpdate() {}
    override fun onEnd() {}
    override val duration: Duration = Duration.ofMinutes(maxDurationMinutes.toLong())
}