package net.miniscape.minigame.api.state

import net.minikloon.fsmgasm.State
import java.time.Duration

class CountdownState(private val spawnFrozen: Boolean, private val countdownSeconds: Int) : State() {
    override fun onStart() {}
    override fun onUpdate() {}
    override fun onEnd() {}
    override val duration: Duration = Duration.ofSeconds(countdownSeconds.toLong())
}