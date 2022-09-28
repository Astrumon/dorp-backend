package ua.features.player

import kotlinx.serialization.Serializable
import ua.features.player.entity.Player

@Serializable
data class PlayerReceive(
    val playerName: String,
    val sessionCode: String
)

@Serializable
data class PlayerResponse(
    val playerId: String
)

@Serializable
data class PlayerListResponse(
    val players: List<Player>
)

