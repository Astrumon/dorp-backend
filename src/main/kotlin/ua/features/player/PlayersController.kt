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

    fun setApplicationCall(call: ApplicationCall) {
        _call = call
    }

    suspend fun joinPlayerToSession() {
        try {
            val receive = call.receive<PlayerReceive>()

            savePlayerToSessionDb(receive)
        } catch (exc: java.lang.NullPointerException) {
            println("Set Application Call first $exc")
        }
    }
    private suspend fun savePlayerToSessionDb(playerReceive: PlayerReceive) {
        try {
            if (!isValidCode(playerReceive.sessionCode))  {
                call.respond(HttpStatusCode.Conflict, "Session code is not valid")
                return
            }

            val session = getSession(playerReceive.sessionCode)
            val sessionId = session.sessionId
            val countOfPlayers = session.countPlayers

            if (getPlayers(sessionId).size < countOfPlayers) {

                if (isNotValidName(playerReceive, sessionId)) return

                val playerId = UUID.randomUUID().toString()
                val playerDto = PlayerDto(playerId, playerReceive.playerName, sessionId, playerReceive.sessionCode)

                Players.insertPlayer(playerDto)
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Limit count players in the one session (session max count players: $countOfPlayers)"
                )
            }
        } catch (exc: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, exc.toString())
        } catch (exc: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't join player to session ${exc.localizedMessage}")
        }
    }

    private suspend fun isNotValidName(playerReceive: PlayerReceive, sessionId: String): Boolean {
        return when {
            !isValidName(playerReceive.playerName) -> {
                call.respond(HttpStatusCode.Conflict, "Player name is not valid")
                true
            }
            !checkPlayerName(playerReceive.playerName, sessionId) -> {
                call.respond(HttpStatusCode.Conflict, "This player name in this $sessionId session is already has")
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