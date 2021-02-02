package net.miniscape.minigame.api.team

import net.miniscape.minigame.api.Game
import net.miniscape.minigame.api.GameModule

class TeamModule(game: Game, private val teamSize: Int, private val teamAmount: Int) : GameModule(game) {
    override fun onEnable() {
        game.properties.teamType = TeamType.TEAM
        game.properties.teamSize = teamSize
        game.properties.teamAmount = teamAmount
    }
}