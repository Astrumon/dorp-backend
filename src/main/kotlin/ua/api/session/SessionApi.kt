package ua.api.session

import ua.database.session.SessionDto
import ua.model.SessionResponseRemote
import java.util.UUID

interface SessionApi {

    fun createSession(sessionDto: SessionDto): SessionResponseRemote

    fun getSessionIdByCode(code: String): UUID?

}