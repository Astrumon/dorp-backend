package ua.database.player.dao

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.transactions.transaction
import ua.database.player.PlayerDto
import java.util.UUID

object Players : Table("players"), PlayerDao {
    val id = integer("id").autoIncrement("players_id_seq")
    val playerId = uuid("player_id").uniqueIndex()
    val playerName = varchar("player_name", 50).uniqueIndex()
    val sessionId = uuid("session_id")
    val sessionCode = varchar("session_code", 20)

    override val primaryKey = PrimaryKey(id, name = "PK_Players")

    override fun insertPlayer(playerDto: PlayerDto): Int? {
        return (transaction {
            insert {
                it[playerId] = playerDto.playerId
                it[playerName] = playerDto.playerName
                it[sessionId] = playerDto.sessionId
                it[sessionCode] = playerDto.sessionCode
            }
        })[id]
    }

    override fun deletePlayerById(playerId: UUID) {
        transaction {
            deleteWhere {
                Players.playerId.eq(playerId)
            }
        }
    }

    override fun getPlayerByName(name: String): PlayerDto? {
        return transaction {
            select {
                (playerName eq name)
            }.mapNotNull {
                it.mapRowToPlayer()
            }.singleOrNull()
        }
    }

    override fun getPlayerById(playerId: UUID): PlayerDto? {
        return transaction {
            select {
                (Players.playerId eq playerId)
            }.mapNotNull {
                it.mapRowToPlayer()
            }.singleOrNull()
        }
    }

    override fun getPlayersBySessionId(sessionId: UUID): List<PlayerDto>? {
        return try {
            transaction {
                select { Players.sessionId eq sessionId }.mapNotNull {
                    it.mapRowToPlayer()
                }
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            null
        }
    }
}

fun ResultRow.mapRowToPlayer() =
    PlayerDto(
        playerId = this[Players.playerId],
        playerName = this[Players.playerName],
        sessionId = this[Players.sessionId],
        sessionCode = this[Players.sessionCode]
    )

interface PlayerDao {

    fun insertPlayer(playerDto: PlayerDto): Int?

    fun deletePlayerById(playerId: UUID)

    fun getPlayerByName(name: String): PlayerDto?

    fun getPlayerById(playerId: UUID): PlayerDto?

    fun getPlayersBySessionId(sessionId: UUID): List<PlayerDto>?
}