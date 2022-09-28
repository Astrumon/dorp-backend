package ua.features.player.entity

import ua.database.player.PlayerDto

@kotlinx.serialization.Serializable
data class Player(
    val playerId: String,
    val playerName: String,
    val sessionId: String,
    val sessionCode: String
)

fun PlayerDto.mapToPlayer() = Player(playerId, playerName, sessionId, sessionCode)