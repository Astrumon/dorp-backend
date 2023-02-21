package ua.modules.injection

import org.koin.dsl.module
import ua.modules.player.PlayerController
import ua.modules.player.PlayerControllerImpl
import ua.modules.session.SessionController
import ua.modules.session.SessionControllerImpl

object ModulesInjection {
    val koinBeans = module {
        single<PlayerController> { PlayerControllerImpl() }
        single<SessionController> { SessionControllerImpl() }
    }
}