package ua

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import org.junit.Test
import ua.model.PlayerReceive
import ua.features.player.configurePlayerRouting
import kotlin.test.assertEquals

class PlayerTest {

   /* private val ApplicationTestBuilder.playerClient: HttpClient
        get() {
            application {
                configurePlayerRouting()
            }
            return createClient {
                install(ContentNegotiation) {
                    json()
                }
            }
        }

    private suspend fun ApplicationTestBuilder.joinPlayerToSession(httpRequestBuilder: HttpRequestBuilder.() -> Unit) =
        playerClient.post(JOIN_PLAYER_URL_PATH) {
            contentType(ContentType.Application.Json)
            httpRequestBuilder()
        }

    @Test
    fun `player must be joined to session`() = testApplication {
        val requestBody = PlayerReceive("John", "3V1WKZ")
        val response = joinPlayerToSession {
            setBody(requestBody)
        }
        println(response.bodyAsText())
        assertEquals(HttpStatusCode.OK, response.status)
    } */

}