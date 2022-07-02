package ua.features.session

import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureSessionRouting() {
    routing {
        post("/sessions/create") {
            val sessionsController = SessionsController(call)
            sessionsController.createSession()
        }
    }
}