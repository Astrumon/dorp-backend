package ua

import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import ua.features.session.configureSessionRouting
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
        configureSessionRouting()
        configureSerialization()
    }.start(wait = true)
}


