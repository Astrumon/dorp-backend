package ua.database.session.dao

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import ua.database.session.SessionDto
import java.util.UUID

object Sessions : Table("sessions"), SessionDao {
    val id = integer("id").autoIncrement()
    val sessionId = uuid("session_id").uniqueIndex()
    val countPlayers = integer("count_players")
    val dateOfCreation = long("date_of_creation")
    val sessionCode = varchar("session_code", 50).uniqueIndex()

    override val primaryKey = PrimaryKey(id, name = "PK_Sessions")

    override fun insertSession(sessionDto: SessionDto) {
        return transaction {
            insert {
                it[sessionId] = sessionDto.sessionId
                it[countPlayers] = sessionDto.countPlayers
                it[dateOfCreation] = sessionDto.dateOfCreation
                it[sessionCode] = sessionDto.sessionCode
            }
        }
    }

    override fun getSessionByCode(sessionCode: String): SessionDto? {
        return transaction {
            select {
                Sessions.sessionCode eq sessionCode
            }.mapNotNull {
                it.mapRowToSession()
            }.singleOrNull()
        }
    }

    override fun getSessionIdByCode(sessionCode: String): UUID? {
        return transaction {
            select {
                Sessions.sessionCode eq sessionCode
            }.map {
                it[sessionId]
            }.singleOrNull()
        }
    }

    private fun ResultRow.mapRowToSession() =
        SessionDto(
            sessionId = this[sessionId],
            countPlayers = this[countPlayers],
            dateOfCreation = this[dateOfCreation],
            sessionCode = this[sessionCode]
        )
}

interface SessionDao {

    fun insertSession(sessionDto: SessionDto)

    fun getSessionByCode(sessionCode: String): SessionDto?

    fun getSessionIdByCode(sessionCode: String): UUID?
}

