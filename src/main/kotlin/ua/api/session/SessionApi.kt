package ua.api.session

import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote
import java.util.UUID

interface SessionApi {

    fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote

    fun getSessionIdByCode(code: String): UUID?

}