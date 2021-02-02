package net.miniscape.minigame.api.map

import net.miniscape.minigame.api.Game
import net.miniscape.minigame.api.GameModule
import java.util.*

class RandomMapModule(game: Game, mapSet: Array<String>) : GameModule(game) {
    private val maps: Array<String> = mapSet
    override fun onEnable() {
        val r = Random()
        val map = r.nextInt(maps.size)
        game.properties.map = maps[map]
    }
}