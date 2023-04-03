package ua.database.task

import ua.model.TaskType
import java.util.UUID

data class TaskDto(
    val taskId: UUID,
    val playerId: UUID,
    val taskDescription: String,
    val taskType: TaskType
)
