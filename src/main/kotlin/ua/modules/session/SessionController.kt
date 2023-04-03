package ua.modules.session

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.api.session.SessionApi
import ua.database.session.SessionDto
import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote
import ua.statuspages.InvalidSessionValidateException
import ua.utils.generateRandomCode
import java.util.*

class SessionControllerImpl: SessionController, KoinComponent {
    private val sessionApi by inject<SessionApi>()

    override fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote {
        val countOfPlayers = sessionReceiveRemote.countOfPlayers

        if (countOfPlayers < 2) throw InvalidSessionValidateException()

        val sessionCode = generateRandomCode()

        val sessionDto = SessionDto(
            sessionId = UUID.randomUUID(),
            countPlayers = sessionReceiveRemote.countOfPlayers,
            dateOfCreation = System.currentTimeMillis(),
            sessionCode = sessionCode
        )

        return sessionApi.createSession(sessionDto)
    }
}

interface SessionController {
    fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote
}