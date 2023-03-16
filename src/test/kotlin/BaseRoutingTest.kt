package ua

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.ktor.plugin.Koin

abstract class BaseRoutingTest {

    protected var koinModules: Module? = null
    protected var moduleList: Application.() -> Unit = {}

    init {
        stopKoin()
    }

    fun <R> withBaseTestApplication(test: suspend TestApplicationEngine.() -> R) {
        testApplication {
            install(ContentNegotiation) { json() }
            koinModules?.let {
                install(Koin) {
                    modules(it)
                }

            }
        }

    }

}