package ua


import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import org.koin.ktor.ext.inject
import ua.api.player.PlayerApi
import ua.api.session.SessionApi
import ua.modules.player.playerModule
import ua.modules.session.sessionModule
import ua.modules.task.taskModule
import ua.statuspages.*

fun Application.applicationModule() {

    install(ContentNegotiation) { json() }

    install(StatusPages) {
        playerStatusPages()
        taskStatusPages()
        sessionStatusPage()

        exception<IllegalArgumentException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }

        exception<UnknownError> { call, _ ->
            call.respondText(
                "Internal server error",
                ContentType.Text.Plain,
                status = HttpStatusCode.InternalServerError
            )
        }

        exception<NullPointerException> { call, exc ->
            call.respond(HttpStatusCode.BadRequest, exc.message ?: "null")
        }
    }

    routing {
        playerModule()
        sessionModule()
        taskModule()
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.sendOk() {
    call.respond(HttpStatusCode.OK)
}