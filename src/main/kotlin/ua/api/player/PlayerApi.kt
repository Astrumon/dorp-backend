package ua.api.player

import ua.model.PlayerListResponse
import ua.model.PlayerReceive
import ua.model.PlayerResponse
import java.util.UUID

interface PlayerApi {

    fun joinPlayerToSession(playerReceive: PlayerReceive, sessionId: UUID): PlayerResponse

    fun getAllPlayers(sessionId: UUID): PlayerListResponse

    fun deletePlayer(playerId: UUID)

}