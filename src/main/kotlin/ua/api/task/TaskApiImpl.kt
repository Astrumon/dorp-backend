package ua.api.task

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ua.database.task.TaskDao
import ua.database.task.TaskDto
import ua.model.TaskReceiveRemote
import ua.statuspages.InvalidTaskException
import java.util.*

object TaskApiImpl : TaskApi, KoinComponent {
    private val taskDao by inject<TaskDao>()

    override fun createTask(taskDto: TaskDto): Boolean {
        return try {
            taskDao.insertTask(taskDto)
            true
        } catch (exc: Exception) {
            throw InvalidTaskException("Cannot create task: ${exc.message}")
        }
    }

    override fun getTask(taskId: UUID): TaskDto {
        return try {
            taskDao.getTaskByTaskId(taskId)
        } catch (exc: Exception) {
            throw InvalidTaskException("Cannot get task by this taskId=$taskId")
        }
    }

    override fun deleteTask(taskId: UUID): Boolean {
        return try {
            taskDao.deleteTaskByTaskId(taskId)
            true
        } catch (exc: Exception) {
            throw InvalidTaskException("Cannot delete this task by this taskId=$taskId")
        }
    }
}