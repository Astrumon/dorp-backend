package ua

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import kotlinx.serialization.Serializable
import ua.plugins.*

fun main() {
    //some change
    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}


