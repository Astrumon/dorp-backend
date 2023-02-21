package ua.model

import kotlinx.serialization.Serializable
import ua.database.player.PlayerDto

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

@kotlinx.serialization.Serializable
data class Player(
    val playerId: String,
    val playerName: String,
    val sessionId: String,
    val sessionCode: String
)

fun PlayerDto.mapToPlayer() = Player(playerId.toString(), playerName, sessionId.toString(), sessionCode)

