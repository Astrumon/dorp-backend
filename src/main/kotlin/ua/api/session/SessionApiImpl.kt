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

    override fun createSession(sessionDto: SessionDto): SessionResponseRemote {
        return try {
            sessionDao.insertSession(sessionDto)
            SessionResponseRemote(sessionDto.sessionCode)
        } catch (exc: java.lang.Exception) {
            throw InvalidSessionException()
        }
    }

    override fun getSessionIdByCode(code: String): UUID? {
        return try {
            sessionDao.getSessionIdByCode(code)
        } catch (exc: Exception) {
            throw InvalidSessionException("cannot get sessionId by sessionCode=$code: $exc")
        }
    }
}