package ua.modules.player

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.api.player.PlayerApi
import ua.api.session.SessionApi
import ua.model.PlayerListResponse
import ua.model.PlayerReceive
import ua.model.PlayerResponse
import ua.statuspages.InvalidPlayerException
import java.util.*

class PlayerControllerImpl : PlayerController, KoinComponent {

    private val playerApi by inject<PlayerApi>()
    private val sessionApi by inject<SessionApi>()

    override fun joinPlayerToSession(playerReceive: PlayerReceive): PlayerResponse {
        val sessionId = sessionApi.getSessionIdByCode(playerReceive.sessionCode)
            ?: throw InvalidPlayerException("cannot find sessionId by this sessionCode=${playerReceive.sessionCode}")

        return playerApi.joinPlayerToSession(playerReceive, sessionId)
    }

    override fun deletePlayer(playerId: UUID?) {
        playerId ?: throw InvalidPlayerException("playerId is null")

        try {
            playerApi.deletePlayer(playerId)
        } catch (exc: Exception) {
            throw InvalidPlayerException("cannot delete player with this playerId=$playerId: $exc")
        }
    }

    override fun getAllPlayers(sessionId: UUID?): PlayerListResponse {
        sessionId ?: throw InvalidPlayerException("sessionId is null")

        return playerApi.getAllPlayers(sessionId)
    }
}

interface PlayerController {
    fun joinPlayerToSession(playerReceive: PlayerReceive): PlayerResponse

    fun deletePlayer(playerId: UUID?)

    fun getAllPlayers(sessionId: UUID?): PlayerListResponse
}