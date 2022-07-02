package ua.features.player

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ua.database.player.PlayerDto
import ua.database.player.Players
import ua.database.session.Sessions
import ua.features.player.entity.Player
import ua.features.player.entity.mapToPlayer
import ua.features.session.SessionResponseRemote
import ua.features.session.entity.Session
import ua.features.session.entity.mapToSession
import java.util.UUID

class PlayersController(val call: ApplicationCall) {

    suspend fun joinPlayerToSession() {
        val receive = call.receive<PlayerReceive>()

        savePlayerToSessionDb(receive)
    }

    private suspend fun savePlayerToSessionDb(playerReceive: PlayerReceive) {
        try {
            val session = getSession(playerReceive.sessionCode)
            val countOfPlayers = session.countPlayers
            val players = getPlayers(session.sessionId)

            if (players.size < countOfPlayers) {

                if (!checkData(playerReceive, session.sessionId)) return

                val playerId = UUID.randomUUID().toString()
                Players.insertPlayer(
                    PlayerDto(
                        playerId,
                        playerReceive.playerName,
                        session.sessionId,
                        playerReceive.sessionCode
                    )
                )
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

    private suspend fun checkData(playerReceive: PlayerReceive, sessionId: String): Boolean {
        return when {
            !checkPlayerName(playerReceive.playerName,sessionId) -> {
                call.respond(HttpStatusCode.Conflict, "This player name is already has")
                 false
            }
            !isValidCode(playerReceive.sessionCode) -> {
                call.respond(HttpStatusCode.Conflict, "Session code is not valid")
               false
            }
            !isValidName(playerReceive.playerName) -> {
                call.respond(HttpStatusCode.Conflict, "Player name is not valid")
                false
            }
            else -> true
        }
    }

    private fun checkPlayerName(name: String, sessionId: String): Boolean {
        val players = getPlayers(sessionId)

        return players.isEmpty() || players.find { it.playerName == name } == null
    }

    private fun isValidCode(code: String): Boolean {
        return true
    }

    private fun isValidName(name: String): Boolean {
        return true
    }

    private fun getSession(sessionCode: String): Session =
        Sessions.getSession(sessionCode)?.mapToSession()
            ?: throw NotFoundException("Session with this $sessionCode code doesn't exist")

    private fun getPlayers(sessionId: String): List<Player> =
        Players.getPlayersBySessionId(sessionId)?.map { it.mapToPlayer() }
            ?: throw NotFoundException("Players with this $sessionId id doesn't exist")
}