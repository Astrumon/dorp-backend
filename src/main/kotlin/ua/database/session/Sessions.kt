package ua.database.session

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

object Sessions: Table("sessions") {
    private val id = Sessions.integer("id")
    private val playerGroupId = Sessions.integer("player_group_id")
    private val countPlayers = Sessions.integer("count_players")
    private val sessionId = Sessions.integer("session_id")

    fun insert(sessionDto: SessionDto) {
        Sessions.insert {
            it[id] = sessionDto.id
            it[playerGroupId] = sessionDto.playerGroupId
            it[countPlayers] = sessionDto.countPlayers
            it[sessionId] = sessionDto.sessionId
        }
    }

    fun getSession(sessionId: Int): SessionDto {
        val sessionModel = Sessions.select { Sessions.sessionId.eq(sessionId) }.single()
        return SessionDto(
            id = sessionModel[Sessions.id],
            playerGroupId = sessionModel[Sessions.playerGroupId],
            countPlayers = sessionModel[Sessions.countPlayers],
            sessionId = sessionModel[Sessions.sessionId]
        )
    }
}