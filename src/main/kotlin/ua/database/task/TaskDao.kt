package ua.database.task

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import ua.model.TaskType
import java.util.UUID


object Tasks: Table("tasks"), TaskDao {
    val id = integer("id").autoIncrement()
    val taskId = uuid("task_id").uniqueIndex()
    val playerId = uuid("player_id")
    val taskDescription = text("task_description")
    val taskType = integer("task_type")

    override val primaryKey = PrimaryKey(id, name = "PK_Tasks")

    override fun insertTask(taskDto: TaskDto) {
        transaction {
            insert {
                it[taskId] = taskDto.taskId
                it[playerId] = taskDto.playerId
                it[taskDescription] = taskDto.taskDescription
                it[taskType] = taskDto.taskType.id
            }
        }
    }

    override fun deleteTaskByTaskId(taskId: UUID) {
        transaction {
            deleteWhere { Tasks.taskId.eq(taskId)  }
        }
    }

    override fun getTaskByTaskId(taskId: UUID): TaskDto {
       return transaction {
            select {
                Tasks.taskId.eq(taskId)
            }.mapNotNull {
                it.mapRowToTask()
            }.single()
        }
    }

    private fun ResultRow.mapRowToTask() =
        TaskDto(
            taskId = this[taskId],
            playerId = this[playerId],
            taskDescription = this[taskDescription],
            taskType = TaskType.getTypeById(this[taskType])
        )

}
interface TaskDao {
    fun insertTask(taskDto: TaskDto)

    fun deleteTaskByTaskId(taskId: UUID)

    fun getTaskByTaskId(taskId: UUID): TaskDto
}