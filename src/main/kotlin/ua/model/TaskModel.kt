package ua.model



@kotlinx.serialization.Serializable
data class TaskReceiveRemote(
    val playerId: String?,
    val taskDescription: String,
    val taskType: Int
)

@kotlinx.serialization.Serializable
data class TaskResponseRemote(
    val taskId: String,
    val playerId: String,
    val taskDescription: String,
    val taskType: Int
)

enum class TaskType(val id: Int) {
    NONE(0),
    PENALTY(1),
    DEFAULT(2);

    companion object {
        fun getTypeById(id: Int) = values().find { it.id == id } ?: NONE
    }
}