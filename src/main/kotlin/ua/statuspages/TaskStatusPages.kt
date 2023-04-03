package ua.statuspages

import io.ktor.http.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun StatusPagesConfig.taskStatusPages() {
    exception<InvalidTaskException> { call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }

    exception<InvalidValidateTaskException> {call, cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message)
    }
}

data class InvalidTaskException(override val message: String = "Invalid task") : Exception()

data class InvalidValidateTaskException(override val message: String = "Invalid task validation") : Exception()