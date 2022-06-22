package ua

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Database
import ua.plugins.*

fun main() {
    Database.connect(
        "jdbc:postgresql://localhost:5432/dorp",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "67tL7WZUjdbi%GT"
    )

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}


