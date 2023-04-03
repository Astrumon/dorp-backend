package ua.api.player

import ua.database.player.PlayerDto
import ua.model.PlayerListResponse
import ua.model.PlayerResponse
import java.util.UUID

interface PlayerApi {

    fun joinPlayerToSession(playerDto: PlayerDto): PlayerResponse

    fun getAllPlayers(sessionId: UUID): PlayerListResponse

    fun deletePlayer(playerId: UUID)

}