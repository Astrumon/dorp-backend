package ua.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun StatusPagesConfig.playerStatusPages() {
    exception<InvalidPlayerException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }

    exception<InvalidSessionCode> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }
}

data class InvalidPlayerException(override val message: String = "Invalid player") : Exception()

data class InvalidSessionCode(
    override val message: String = "Invalid session code, session code must be 6 of length and include A..Z symbols"
) : Exception()