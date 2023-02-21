package ua.model

@kotlinx.serialization.Serializable
data class SessionReceiveRemote(
    val countOfPlayers: Int
)

@kotlinx.serialization.Serializable
data class SessionResponseRemote(
    val sessionCode: String
)