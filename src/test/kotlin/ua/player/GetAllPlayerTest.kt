package ua.player

import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import ua.model.PlayerListResponse
import ua.statuspages.InvalidPlayerException
import java.util.UUID
import kotlin.test.assertEquals

class GetAllPlayerTest: BasePlayerTest() {


    @Test
    fun `throw InvalidPlayerException when user send null session id`() {
        assertThrows<InvalidPlayerException> {
            playerController.getAllPlayers(null)
        }
    }

    @Test
    fun `get all players`() {
        val sessionId = UUID.randomUUID()

        val mockPlayerListResponse = mockk<PlayerListResponse>()

        coEvery { playerApi.getAllPlayers(sessionId) } returns mockPlayerListResponse

        assertEquals(mockPlayerListResponse, playerController.getAllPlayers(sessionId))
    }
}