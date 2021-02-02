package net.miniscape.minigame.api.state

import net.minikloon.fsmgasm.State
import java.time.Duration

class WinCeremonyState(private val stateDurationSeconds: Int) : State() {
    override fun onStart() {}
    override fun onUpdate() {}
    override fun onEnd() {}
    override val duration: Duration = Duration.ofSeconds(stateDurationSeconds.toLong())
}