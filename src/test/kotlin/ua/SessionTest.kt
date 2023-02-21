package ua

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.Test
import ua.features.session.CREATE_SESSION_URL_PATH
import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote
import ua.features.session.configureSessionRouting
import java.util.regex.Pattern
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

    private suspend fun ApplicationTestBuilder.createSession(httpRequestBuilder: HttpRequestBuilder.() -> Unit) =
        sessionClient.post(CREATE_SESSION_URL_PATH) {
            contentType(ContentType.Application.Json)
            httpRequestBuilder()
        }

    @Test
    fun `session must be created`() = testApplication {
        val response = createSession {
            setBody(SessionReceiveRemote(2))
        }
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Test
    fun `count of players must be more or equal than 2`() = testApplication {
        val invalidParam = SessionReceiveRemote(1)
        val response = createSession {
            setBody(invalidParam)
        }
        assertEquals(HttpStatusCode.BadRequest, response.status)
    }

    @Test
    fun `result must be include uppercase symbols or numbers`() = testApplication {
        val response = createSession {
            setBody(SessionReceiveRemote(2))
        }
        val sessionCode = response.body<SessionResponseRemote>().sessionCode
        val isValid = Pattern.compile("[A-Z-\\d]{6}").matcher(sessionCode).find()

        assertEquals(isValid, true)
    }
}