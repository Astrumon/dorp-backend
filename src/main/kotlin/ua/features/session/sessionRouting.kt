package ua.features.session

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import ua.di.mainModule

fun Application.configureSessionRouting() {

    val sessionsController: SessionsController by inject()

    routing {
        post("/sessions/create") {
            sessionsController.createSession(call)
        }
    }
}