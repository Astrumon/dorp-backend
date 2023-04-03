package ua.modules.injection

import org.koin.dsl.module
import ua.modules.player.PlayerController
import ua.modules.player.PlayerControllerImpl
import ua.modules.session.SessionController
import ua.modules.session.SessionControllerImpl
import ua.modules.task.TaskController
import ua.modules.task.TaskControllerImpl

object ModulesInjection {
    val koinBeans = module {
        single<PlayerController> { PlayerControllerImpl() }
        single<SessionController> { SessionControllerImpl() }
        single<TaskController> { TaskControllerImpl() }
    }
}