package ua.database.session

data class SessionDto(
    val id: Int,
    val playerGroupId: Int,
    val countPlayers: Int,
    val sessionId: Int
)
