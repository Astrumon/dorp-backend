package ua.features.player

@kotlinx.serialization.Serializable
data class PlayerReceive(
    val playerName: String,
    val sessionCode: String
)
