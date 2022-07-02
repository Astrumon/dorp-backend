package ua.database.player

data class PlayerDto(
    val playerId: String,
    val playerName: String,
    val sessionId: String,
    val sessionCode: String
)
