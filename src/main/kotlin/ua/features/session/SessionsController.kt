package ua.features.session

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.jetbrains.exposed.exceptions.ExposedSQLException
import ua.database.session.SessionDto
import ua.database.session.Sessions
import ua.utils.generateRandomCode
import ua.utils.getCurrentDate
import java.util.UUID

class SessionsController(val call: ApplicationCall) {
    suspend fun createSession() {
        val receive = call.receive<SessionReceiveRemote>()
        val countOfPlayers = receive.countOfPlayers
        if (countOfPlayers < 2) call.respond(HttpStatusCode.BadRequest, "Count of players must be more than 1")

        val sessionId = UUID.randomUUID().toString()
        val sessionCode = generateRandomCode(6)

        val sessionDto = SessionDto(sessionId, countOfPlayers, getCurrentDate("dd/M/yyyy hh:mm:ss"), sessionCode)

        saveSessionToDb(sessionDto)

        call.respond(SessionResponseRemote(sessionCode))
    }

    private suspend fun saveSessionToDb(sessionDto: SessionDto) {
        try {
            Sessions.insert(sessionDto)
        } catch (exc: ExposedSQLException) {
            call.respond(HttpStatusCode.Conflict, exc.toString())
        } catch (exc: Exception) {
            call.respond(HttpStatusCode.BadRequest, "Can't create session ${exc.localizedMessage}")
        }
    }
}