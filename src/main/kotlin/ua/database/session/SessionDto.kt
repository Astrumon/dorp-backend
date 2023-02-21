package ua.database.session

import java.util.UUID

data class SessionDto(
    val sessionId: UUID,
    val countPlayers: Int,
    val dateOfCreation: Long,
    val sessionCode: String
)
