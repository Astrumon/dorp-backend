package ua.modules.session

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.api.session.SessionApi
import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote

class SessionControllerImpl: SessionController, KoinComponent {
    private val sessionApi by inject<SessionApi>()

    override fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote {
        return sessionApi.createSession(sessionReceiveRemote)
    }
}

interface SessionController {
    fun createSession(sessionReceiveRemote: SessionReceiveRemote): SessionResponseRemote
}