package ua.features.session.entity

import ua.database.session.SessionDto

data class Session(
    val sessionId: String,
    val countPlayers: Int,
    val dateOfCreation: String,
    val sessionCode: String
)

fun SessionDto.mapToSession(): Session = Session(sessionId, countPlayers, dateOfCreation, sessionCode)

