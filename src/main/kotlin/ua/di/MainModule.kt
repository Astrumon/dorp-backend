package ua.di

import org.koin.dsl.module
import ua.features.player.PlayersController
import ua.features.session.SessionsController

val mainModule = module {
    single {
        PlayersController()
    }
    single {
        SessionsController()
    }
}

