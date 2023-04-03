package ua.api.task

import ua.database.task.TaskDto
import java.util.UUID

interface TaskApi {

    fun createTask(taskDto: TaskDto): Boolean

    fun getTask(taskId: UUID): TaskDto

    fun deleteTask(taskId: UUID): Boolean
}