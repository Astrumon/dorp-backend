package ua.features.session

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSessionRouting() {
    routing {
        post("/session") {
            val sessionsController = SessionsController(call)
            sessionsController.createSession()
        }
    }
}