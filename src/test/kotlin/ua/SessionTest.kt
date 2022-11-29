package ua

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.Test
import ua.features.session.SessionReceiveRemote
import ua.features.session.configureSessionRouting
import kotlin.test.assertEquals

class SessionTest {

    private val ApplicationTestBuilder.sessionClient: HttpClient
        get() {
            application {
                configureSessionRouting()
            }
            return createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
        }

    @Test
    fun testCreateSession() = testApplication {
        val response = sessionClient.post("/sessions/create") {
            contentType(ContentType.Application.Json)
            setBody(SessionReceiveRemote(2))
        }
        println(response.bodyAsText())
        assertEquals(HttpStatusCode.OK, response.status)
    }


}