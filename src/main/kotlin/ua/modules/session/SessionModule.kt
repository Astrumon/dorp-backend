package ua.modules.session

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ua.model.SessionReceiveRemote

fun Route.sessionModule() {
    val controller by inject<SessionController>()

    post(SESSION_CREATE_URL_PATH) {
        val receive = call.receive<SessionReceiveRemote>()
        println(receive.countOfPlayers.toString())
        val response = controller.createSession(receive)
        call.respond(HttpStatusCode.OK, response)
    }
}

const val SESSION_CREATE_URL_PATH = "/sessions/create"