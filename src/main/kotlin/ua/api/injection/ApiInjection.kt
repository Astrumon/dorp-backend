package ua.api.injection

import org.koin.dsl.module
import ua.api.player.PlayerApi
import ua.api.player.PlayerApiImpl
import ua.api.session.SessionApi
import ua.api.session.SessionApiImpl
import ua.api.task.TaskApi
import ua.api.task.TaskApiImpl

object ApiInjection {
    val koinBeans = module {
        single<PlayerApi> { PlayerApiImpl }
        single<SessionApi> { SessionApiImpl }
        single<TaskApi> { TaskApiImpl }
    }
}