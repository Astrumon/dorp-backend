package ua.features.player

import io.ktor.server.application.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject


fun Application.configurePlayerRouting() {
    val playersController: PlayersController by inject()

    routing {
        post("/players/join") {
            playersController.joinPlayerToSession(call)
        }

        delete("/players/{player_id}/delete") {
            playersController.deletePlayer(call)
        }

        delete("/players/{player_id}/exit") {

        }
    }
}