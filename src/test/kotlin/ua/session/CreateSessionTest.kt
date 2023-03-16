package ua.session

import io.mockk.mockk
import org.junit.Test
import ua.modules.session.SessionController
import io.mockk.clearMocks
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.assertThrows
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ua.api.session.SessionApi
import ua.model.SessionReceiveRemote
import ua.model.SessionResponseRemote
import ua.modules.session.SessionControllerImpl
import ua.statuspages.InvalidSessionValidateException

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CreateSessionTest {

    private val sessionApi: SessionApi = mockk()
    private val sessionController: SessionController by lazy { SessionControllerImpl() }
    private val sessionResponse = SessionResponseRemote("F2FJIL")

    init {
        startKoin {
            modules(
                module {
                    single {
                        sessionApi
                    }
                },
                module {
                    single {
                        sessionController
                    }
                }
            )
        }
    }

    @BeforeEach
    fun before() {
        clearMocks(sessionApi)
    }

    @Test
    fun `when user successfully create session`() {

        val postBody = SessionReceiveRemote(4)

        coEvery { sessionController.createSession(postBody) } returns sessionResponse

        runBlocking {
            val response = sessionController.createSession(postBody)

            assertThat(response).isEqualTo(sessionResponse)
        }
    }

    @Test
    fun `when user want to create session with count of players less 2`() {
        val postBody = SessionReceiveRemote(1)

        assertThrows<InvalidSessionValidateException> {
            runBlocking {
                sessionController.createSession(postBody)
            }
        }
    }
}