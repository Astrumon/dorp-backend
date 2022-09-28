package ua.features.player

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.koin.java.KoinJavaComponent.inject
import ua.database.player.PlayerDto
import ua.database.player.Players
import ua.database.session.Sessions
import ua.features.player.entity.Player
import ua.features.player.entity.mapToPlayer
import ua.features.session.entity.Session
import ua.features.session.entity.mapToSession
import java.util.UUID

class PlayersController {
    private var _call: ApplicationCall? = null
    private val call: ApplicationCall get() = _call!!

    suspend fun joinPlayerToSession(call: ApplicationCall) {
        _call = call
        val receive = call.receive<PlayerReceive>()

        val session = getSession(receive.sessionCode)

        val playerId = UUID.randomUUID().toString()
        val playerDto = PlayerDto(playerId, receive.playerName, session.sessionId, receive.sessionCode)

        if (savePlayerToSessionDb(playerDto, session.countPlayers)) {
            call.respond(HttpStatusCode.OK, PlayerResponse(playerId))
        }
    }

    suspend fun deletePlayer(call: ApplicationCall) {
        val playerId = call.parameters["player_id"]
        if (!playerId.isNullOrEmpty()) {
            if (Players.deletePlayerById(playerId)) call.respond(HttpStatusCode.OK)
            else call.respond(HttpStatusCode.NotFound, "Player with $playerId cannot find")
        } else {
            call.respond(HttpStatusCode.BadRequest, "Player with $playerId cannot delete")
        }
    }

    suspend fun getAllPlayers(call: ApplicationCall) {
        val sessionId = call.parameters["session_id"]
        if (!sessionId.isNullOrEmpty()) {
            val players = Players.getPlayersBySessionId(sessionId) ?: emptyList()
            if (players.isNotEmpty()) call.respond(PlayerListResponse(players.map { it.mapToPlayer() }))
            else call.respond(HttpStatusCode.BadRequest, "Cannot find players with this $sessionId id")
        } else {
            call.respond(HttpStatusCode.BadRequest, "this $sessionId id is doesn't exist")
        }
    }

    private suspend fun savePlayerToSessionDb(playerDto: PlayerDto, countOfPlayers: Int): Boolean {
        return try {
            if (!isValidCode(playerDto.sessionCode)) {
                call.respond(HttpStatusCode.Conflict, "Session code is not valid")
                return false
            }

            return if (getPlayers(playerDto.sessionId).size < countOfPlayers) {

                if (isNotValidName(playerDto)) return false

                Players.insertPlayer(playerDto)
                true
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Limit count players in the one session (session max count players: $countOfPlayers)"
                )
                false
            }
        } catch (exc: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, exc.toString())
            false
        } catch (exc: NullPointerException) {
            exc.printStackTrace()
            false
        } catch (exc: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't join player to session ${exc.localizedMessage}")
            false
        }
    }

    private suspend fun isNotValidName(playerDto: PlayerDto): Boolean {
        return when {
            !isValidName(playerDto.playerName) -> {
                call.respond(HttpStatusCode.Conflict, "Player name is not valid")
                true
            }
            !checkPlayerName(playerDto.playerName, playerDto.sessionId) -> {
                call.respond(
                    HttpStatusCode.Conflict,
                    "This player name in this ${playerDto.sessionId} session is already has"
                )
                true
            }
            else -> false
        }
    }

    private fun checkPlayerName(name: String, sessionId: String): Boolean {
        val players = getPlayers(sessionId)

        return players.isEmpty() || players.find { it.playerName == name } == null
    }

    private fun isValidCode(code: String): Boolean = code.matches(Regex("^[A-Z\\d]{6}+"))

    private fun isValidName(name: String): Boolean = name.length <= 10

    private fun getSession(sessionCode: String): Session =
        Sessions.getSession(sessionCode)?.mapToSession()
            ?: throw NotFoundException("Session with this $sessionCode code doesn't exist")

    private fun getPlayers(sessionId: String): List<Player> =
        Players.getPlayersBySessionId(sessionId)?.map { it.mapToPlayer() }
            ?: throw NotFoundException("Players with this $sessionId id doesn't exist")
}