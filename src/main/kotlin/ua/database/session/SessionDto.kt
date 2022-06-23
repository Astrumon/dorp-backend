package ua.database.session

data class SessionDto(
    val sessionId: String,
    val countPlayers: Int,
    val dateOfCreation: String,
    val sessionCode: String
)
