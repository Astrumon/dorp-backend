package ua.player

import io.mockk.coEvery
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import ua.statuspages.InvalidPlayerException
import java.util.UUID
import org.junit.jupiter.api.Assertions.assertDoesNotThrow

class DeletePlayerTest: BasePlayerTest() {

    @Test
    fun `throw InvalidPlayerException when user send null playerId`() {
        assertThrows<InvalidPlayerException> {
            playerController.deletePlayer(null)
        }
    }

    @Test
    fun `delete player by playerId`() {
        val playerId = UUID.randomUUID()

        coEvery { playerApi.deletePlayer(playerId) } returns Unit

        assertDoesNotThrow {
            playerController.deletePlayer(playerId.toString())
        }
    }
}