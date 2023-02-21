package ua.database.player

import java.util.UUID

data class PlayerDto(
    val playerId: UUID,
    val playerName: String,
    val sessionId: UUID,
    val sessionCode: String
)
