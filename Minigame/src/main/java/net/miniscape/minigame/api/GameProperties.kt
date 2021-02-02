package net.miniscape.minigame.api

import net.miniscape.minigame.api.team.TeamType
import java.util.*

class GameProperties {
    var map = "world"
    var teamType = TeamType.SOLO
    var teamSize = 4
    var teamAmount = 4
    val allowBreak: List<Int> = ArrayList()
    var allowPlace: List<Int> = ArrayList()
    var denyBreak: List<Int> = ArrayList()
    var denyPlace: List<Int> = ArrayList()
    var blockBreak = false
    var blockPlace = false
    var modifyInventory = true
    var flight = false
    var respawn = true
    var respawnDelay = 5
    var joinMidgame = false
    var dropItems = false
    var pickupItems = false
    var deathDropItems = false
    var hunger = false
    var pvp = true
    var pvpDamage = true
    var teamDamage = false
    var minPlayers = 4
    var maxPlayers = 8
    var enforceMax = false
}