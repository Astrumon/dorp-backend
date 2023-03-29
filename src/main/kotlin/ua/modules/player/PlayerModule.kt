package ua.modules.player

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ua.model.PlayerReceive
import ua.sendOk
import java.util.*

fun Route.playerModule() {

    val controller by inject<PlayerController>()

    post(JOIN_PLAYER_URL_PATH) {
        val receive = call.receive<PlayerReceive>()
        val response = controller.joinPlayerToSession(receive)
        call.respond(HttpStatusCode.OK, response)
    }

    get(GET_PLAYERS_URL_PATH) {
        val sessionId = call.parameters["session_id"]
        val response = controller.getAllPlayers(UUID.fromString(sessionId))
        call.respond(HttpStatusCode.OK, response)
    }

    delete(PLAYER_EXIT_URL_PATH) {
        val playerId = call.parameters["player_id"]
        controller.deletePlayer(playerId)
        sendOk()
    }
}

const val JOIN_PLAYER_URL_PATH = "/players/join"
const val PLAYER_EXIT_URL_PATH = "/players/{player_id}/exit"
const val GET_PLAYERS_URL_PATH = "/players/list/{session_id}"