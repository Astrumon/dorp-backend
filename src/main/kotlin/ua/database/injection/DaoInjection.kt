package ua.database.injection

import org.koin.dsl.module
import ua.database.player.dao.PlayerDao
import ua.database.player.dao.Players
import ua.database.session.dao.SessionDao
import ua.database.session.dao.Sessions
import ua.database.task.TaskDao
import ua.database.task.Tasks

object DaoInjection {
    val koinBeans = module {
        single<PlayerDao> { Players }
        single<SessionDao> { Sessions}
        single<TaskDao> { Tasks }
    }
}