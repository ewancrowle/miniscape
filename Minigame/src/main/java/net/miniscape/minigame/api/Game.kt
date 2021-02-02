package net.miniscape.minigame.api

import net.minikloon.fsmgasm.StateSeries
import net.miniscape.game.GameType
import net.miniscape.minigame.api.state.*
import java.util.*

abstract class Game(type: GameType, nameKey: String = "GAME") {
    val properties: GameProperties
    val series: StateSeries = StateSeries()
    private val modules: MutableSet<GameModule>
    val players: Set<UUID>

    protected open fun addStates() {
        series.add(createPregameState())
        series.add(SpawnPlayersState(this))
        series.add(CountdownState(true, 5))
        series.add(RunningState(20))
        series.add(WinCeremonyState(10))
        series.add(EndgameState(this))
    }

    protected open fun addModules() {
    }

    private fun onGameStart() {
        modules.stream().forEach { module: GameModule? -> onGameStart() }
    }

    protected fun addModule(module: GameModule) {
        modules.add(module)
        module.onEnable()
    }

    protected fun createPregameState(): PregameState {
        return PregameState(this, properties.minPlayers, properties.maxPlayers, properties.enforceMax)
    }

    init {
        modules = HashSet()
        properties = GameProperties()
        addStates()
        addModules()
        players = HashSet()
        series.start()
    }
}