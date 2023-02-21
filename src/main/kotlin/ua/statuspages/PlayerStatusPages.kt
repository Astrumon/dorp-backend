package ua.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*


fun StatusPagesConfig.playerStatusPages() {
    exception<InvalidPlayerException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
}

data class InvalidPlayerException(override val message: String = "Invalid player") : Exception()