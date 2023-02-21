package ua.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.sessionStatusPage() {
    exception<InvalidPlayerException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
    exception<InvalidSessionValidateException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.localizedMessage)
    }
}

data class InvalidSessionException(override val message: String = "Invalid session") : Exception()

data class InvalidSessionValidateException(
    override val message: String =
        "Invalid countOfPlayers value, count must be >= 2"
) : Exception()