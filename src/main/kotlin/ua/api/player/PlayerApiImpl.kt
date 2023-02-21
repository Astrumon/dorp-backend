package ua.api.player

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.database.player.PlayerDto
import ua.database.player.dao.PlayerDao
import ua.model.PlayerListResponse
import ua.model.PlayerReceive
import ua.model.PlayerResponse
import ua.model.mapToPlayer
import ua.statuspages.InvalidPlayerException
import java.util.*

object PlayerApiImpl : PlayerApi, KoinComponent {

    private val playerDao by inject<PlayerDao>()

    override fun joinPlayerToSession(playerReceive: PlayerReceive, sessionId: UUID): PlayerResponse {
        val playerId = UUID.randomUUID()
        val playerDto = PlayerDto(
            playerId = playerId,
            playerName = playerReceive.playerName,
            sessionCode = playerReceive.sessionCode,
            sessionId = sessionId
        )

        playerDao.insertPlayer(playerDto) ?: throw InvalidPlayerException("Error while creating user")
        return PlayerResponse(playerId.toString())
    }

    override fun getAllPlayers(sessionId: UUID): PlayerListResponse {
        val players = playerDao.getPlayersBySessionId(sessionId)?.map { it.mapToPlayer() }
            ?: throw InvalidPlayerException("Error while get all players")

        return PlayerListResponse(players = players)
    }

    override fun deletePlayer(playerId: UUID) = playerDao.deletePlayerById(playerId)
}