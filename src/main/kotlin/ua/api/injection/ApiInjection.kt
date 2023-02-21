package ua.api.injection

import org.koin.dsl.module
import ua.api.player.PlayerApi
import ua.api.player.PlayerApiImpl
import ua.api.session.SessionApi
import ua.api.session.SessionApiImpl

object ApiInjection {
    val koinBeans = module {
        single<PlayerApi> { PlayerApiImpl }
        single<SessionApi> { SessionApiImpl }
    }
}