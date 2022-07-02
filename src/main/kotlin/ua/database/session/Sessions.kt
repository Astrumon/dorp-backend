package ua.database.session

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

object Sessions : Table("sessions") {
    private val sessionId = Sessions.varchar("session_id", 50).uniqueIndex()
    private val countPlayers = Sessions.integer("count_players")
    private val dateOfCreation = Sessions.varchar("date_of_creation", 50)
    private val sessionCode = Sessions.varchar("session_code", 50).uniqueIndex()

    fun insert(sessionDto: SessionDto) {
        transaction {
            Sessions.insert {
                it[sessionId] = sessionDto.sessionId
                it[countPlayers] = sessionDto.countPlayers
                it[dateOfCreation] = sessionDto.dateOfCreation
                it[sessionCode] = sessionDto.sessionCode
            }
        }
    }

    fun getSession(sessionCode: String): SessionDto? {
        return try {
            transaction {
                val sessionModel = Sessions.select { Sessions.sessionCode.eq(sessionCode) }.single()
                SessionDto(
                    sessionId = sessionModel[sessionId],
                    dateOfCreation = sessionModel[dateOfCreation],
                    countPlayers = sessionModel[countPlayers],
                    sessionCode = sessionModel[Sessions.sessionCode]
                )
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            null
        }
    }
}