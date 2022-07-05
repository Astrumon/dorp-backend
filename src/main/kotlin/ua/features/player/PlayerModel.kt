package ua.features.player

import kotlinx.serialization.Serializable

@Serializable
data class PlayerReceive(
    val playerName: String,
    val sessionCode: String
)

@Serializable
data class PlayerResponse(
    val playerId: String
)

