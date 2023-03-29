package ua.player

import io.mockk.coEvery
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import ua.model.PlayerReceive
import ua.model.PlayerResponse
import ua.statuspages.InvalidSessionCode
import kotlin.test.assertEquals


class JoinPlayerTest: BasePlayerTest() {

    @Test
    fun `successful join player to session`() {
        val expectedPlayerResponse = PlayerResponse(playerIdMockData.toString())
        coEvery {  playerController.joinPlayerToSession(playerReceiveMockData)} returns expectedPlayerResponse
        val playerResponse = playerController.joinPlayerToSession(playerReceiveMockData)

        assertEquals(expectedPlayerResponse, playerResponse)
    }

    @Test
    fun `throw InvalidSessionCode exception if sessionCode has less than 6 symbol`() {
        assertThrows<InvalidSessionCode> {
            playerController.joinPlayerToSession(PlayerReceive("Bob", "FDG3"))
        }
    }

    @Test
    fun `throw InvalidSessionCode exception if sessionCode has more than 6 symbol`() {
        assertThrows<InvalidSessionCode> {
            playerController.joinPlayerToSession(PlayerReceive("Bob", "FDG3GD1GS2"))
        }
    }
    @Test
    fun `throw InvalidSessionCode exception if sessionCode has 6 symbols but has invalid symbol`() {
        assertThrows<InvalidSessionCode> {
            playerController.joinPlayerToSession(PlayerReceive("Bob", "fD5G3G"))
        }
    }

    @Test
    fun `throw InvalidSessionCode exception if sessionCode has 6 symbols but has special symbols`() {
        assertThrows<InvalidSessionCode> {
            playerController.joinPlayerToSession(PlayerReceive("Bob", "H_OG'G"))
        }
    }
}