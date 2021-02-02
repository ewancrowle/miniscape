package net.miniscape.minigame.api

abstract class GameModule(val game: Game) {
    open fun onEnable() {}
    open fun onGameStart() {}
}