package ua.database.session

import ua.features.session.SessionReceiveRemote
import ua.features.session.entity.Session

data class SessionDto(
    val sessionId: String,
    val countPlayers: Int,
    val dateOfCreation: String,
    val sessionCode: String
)
