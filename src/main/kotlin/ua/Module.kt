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
import ua.statuspages.*

fun Application.applicationModule() {

    install(ContentNegotiation) { json() }

    install(StatusPages) {
        playerStatusPages()

        exception<IllegalArgumentException> { call, _ ->
            call.respond(HttpStatusCode.BadRequest)
        }

        exception<InvalidSessionValidateException> { call, exc ->
            call.respond(HttpStatusCode.BadRequest, exc.message)
        }

        exception<InvalidSessionException> { call, exc ->
            call.respond(HttpStatusCode.BadRequest, exc.message)
        }

        exception<InvalidPlayerException> { call, exc ->
            call.respond(HttpStatusCode.BadRequest, exc.message)
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
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.sendOk() {
    call.respond(HttpStatusCode.OK)
}