package ua.modules.task

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.api.task.TaskApi
import ua.database.task.TaskDto
import ua.model.TaskReceiveRemote
import ua.model.TaskResponseRemote
import ua.model.TaskType
import ua.statuspages.InvalidValidateTaskException
import ua.utils.isNullOrEmptyString
import java.util.*

class TaskControllerImpl : TaskController, KoinComponent {
    private val taskApi by inject<TaskApi>()

    override fun createTask(taskReceiveRemote: TaskReceiveRemote) {
        val playerId = taskReceiveRemote.playerId

        if (playerId.isNullOrEmptyString()) throw InvalidValidateTaskException("playerId is null or empty")

        val taskId = UUID.randomUUID()
        val taskDto = with(taskReceiveRemote) {
            TaskDto(
                taskId = taskId,
                playerId = UUID.fromString(playerId),
                taskDescription = taskDescription,
                taskType = TaskType.getTypeById(taskType)
            )
        }

        taskApi.createTask(taskDto)
    }

    override fun deleteTask(taskId: String?): Boolean {
        if (taskId.isNullOrEmptyString()) throw  InvalidValidateTaskException("taskId is null or empty")
        return taskApi.deleteTask(UUID.fromString(taskId))
    }

    override fun getTask(taskId: String?): TaskResponseRemote {
        if (taskId.isNullOrEmptyString()) throw InvalidValidateTaskException("taskId is null or empty")

        val taskDto = taskApi.getTask(UUID.fromString(taskId))

        return with(taskDto) {
            TaskResponseRemote(
                taskId = taskId!!,
                playerId = playerId.toString(),
                taskDescription = taskDescription,
                taskType = taskType.id
            )
        }
    }
}

interface TaskController {
    fun createTask(taskReceiveRemote: TaskReceiveRemote)

    fun deleteTask(taskId: String?): Boolean

    fun getTask(taskId: String?): TaskResponseRemote
}