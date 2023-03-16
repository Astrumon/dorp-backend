package ua.api.session

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.database.session.SessionDto
import ua.database.session.dao.SessionDao
import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote
import ua.statuspages.InvalidSessionException
import ua.statuspages.InvalidSessionValidateException
import ua.utils.generateRandomCode
import java.util.*

object SessionApiImpl : SessionApi, KoinComponent {

    private val sessionDao by inject<SessionDao>()

    override fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote {
        val sessionCode = generateRandomCode(6)

        val dto = SessionDto(
            sessionId = UUID.randomUUID(),
            countPlayers = sessionReceiveRemote.countOfPlayers,
            dateOfCreation = System.currentTimeMillis(),
            sessionCode = sessionCode
        )

        try {
            sessionDao.insertSession(dto)
        } catch (exc: java.lang.Exception) {
            throw InvalidSessionException()
        }
        return SessionResponseRemote(sessionCode)
    }

    override fun getSessionIdByCode(code: String): UUID? {
        return try {
            sessionDao.getSessionIdByCode(code)
        } catch (exc: Exception) {
            throw InvalidSessionException("cannot get sessionId by sessionCode=$code: $exc")
        }
    }
}