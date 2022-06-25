package ua.features.session

@kotlinx.serialization.Serializable
data class SessionReceiveRemote(
    val countOfPlayers: Int
)

@kotlinx.serialization.Serializable
data class SessionResponseRemote(
    val sessionCode: String
)