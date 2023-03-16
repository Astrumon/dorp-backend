package ua.modules.session

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.api.session.SessionApi
import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote
import ua.statuspages.InvalidSessionValidateException

class SessionControllerImpl: SessionController, KoinComponent {
    private val sessionApi by inject<SessionApi>()

    override fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote {
        val countOfPlayers = sessionReceiveRemote.countOfPlayers

        if (countOfPlayers < 2) throw InvalidSessionValidateException()

        return sessionApi.createSession(sessionReceiveRemote)
    }
}

interface SessionController {
    fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote
}