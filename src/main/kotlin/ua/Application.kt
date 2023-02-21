package ua

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.cio.*
import org.jetbrains.exposed.sql.Database
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import ua.api.injection.ApiInjection
import ua.database.injection.DaoInjection
import ua.modules.injection.ModulesInjection

fun main(args: Array<String>) {
    embeddedServer(CIO, commandLineEnvironment(args)).start(wait = true)
}

fun Application.module() {
    val dbPassword = environment.config.property("db.dbPass").getString()
    val dbUrl = environment.config.property("db.dbUrl").getString()
    val dbUser = environment.config.property("db.dbUser").getString()

    connectToDb(dbUrl, dbUser, dbPassword)
    install(Koin) {
        slf4jLogger()
        modules(
            ApiInjection.koinBeans,
            DaoInjection.koinBeans,
            ModulesInjection.koinBeans
        )
    }
    applicationModule()
}

fun connectToDb(url: String, user: String, password: String) {
    Database.connect(
        url,
        driver = "org.postgresql.Driver",
        user = user,
        password = password
    )
}



