package ua.database.player

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

object Players : Table("players") {
    val playerId = Players.varchar("player_id", 50).uniqueIndex()
    val playerName = Players.varchar("player_name", 10)
    val sessionId = Players.varchar("session_id", 50)
    val sessionCode = Players.varchar("session_code", 20)

    fun insertPlayer(playerDto: PlayerDto) {
        transaction {
            Players.insert {
                it[playerId] = playerDto.playerId
                it[playerName] = playerDto.playerName
                it[sessionId] = playerDto.sessionId
                it[sessionCode] = playerDto.sessionCode
            }
        }
    }

    fun deletePlayerById(playerId: String): Boolean {
        return try {
            transaction {
                deleteWhere {
                    this@Players.playerId.eq(playerId)
                }
            }
            true
        } catch (exc: Exception) {
            exc.printStackTrace()
            false
        }
    }

    fun getPlayerByName(name: String): PlayerDto? {
        return  try {
            transaction {
                val playerModel = select { playerName.eq(name) }.single()
                PlayerDto(
                    playerId = playerModel[playerId],
                    playerName = playerModel[playerName],
                    sessionId = playerModel[sessionId],
                    sessionCode = playerModel[sessionCode]
                )
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            null
        }
    }

    fun getPlayersBySessionId(sessionId: String): List<PlayerDto>? {
        return  try {
            transaction {
                val playerModel = selectAll()
                playerModel.map {
                    PlayerDto(
                        playerId = it[playerId],
                        playerName = it[playerName],
                        sessionId = it[Players.sessionId],
                        sessionCode = it[sessionCode]
                    )
                }
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
            null
        }
    }


}