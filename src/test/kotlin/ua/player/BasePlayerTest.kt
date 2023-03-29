package ua.player

import io.mockk.clearMocks
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ua.api.player.PlayerApi
import ua.api.session.SessionApi
import ua.model.PlayerReceive
import ua.modules.player.PlayerController
import ua.modules.player.PlayerControllerImpl
import java.util.UUID

abstract class BasePlayerTest {
    val playerApi: PlayerApi = mockk()
    val sessionApi: SessionApi = mockk()
    val playerController: PlayerController by lazy { PlayerControllerImpl() }

    val playerReceiveMockData = PlayerReceive("Bob", "FJ2FI4")

    val sessionIdMockData = UUID.fromString("b1ca87f2-2b8c-4f5d-9e80-034f3b8d14c1")

    val playerIdMockData = UUID.fromString("d0556b1e-e84f-46d2-b9f9-6bb4d6f4e6f0")

    init {
        if (GlobalContext.getOrNull() == null) {
            startKoin {
                modules(
                    module {
                        single {
                            playerApi
                        }
                    },
                    module {
                        single {
                            sessionApi
                        }
                    },
                    module {
                        single {
                            playerController
                        }
                    }
                )
            }
        }
    }

    @BeforeEach
    fun before() {
        clearMocks(playerApi, sessionApi)
    }
}