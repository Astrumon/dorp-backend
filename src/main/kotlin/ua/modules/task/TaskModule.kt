package ua.modules.task

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject
import ua.model.TaskReceiveRemote
import ua.sendOk

fun Route.taskModule() {
    val controller by inject<TaskController>()

    post(TASK_CREATE_URL_PATH) {
        val receive = call.receive<TaskReceiveRemote>()
        controller.createTask(receive)
        sendOk()
    }

    get(TASK_GET_URL_PATH) {
        val taskId = call.parameters["task_id"]
        val response = controller.getTask(taskId)
        call.respond(HttpStatusCode.OK, response)
    }

    delete(TASK_DELETE_URL_PATH) {
        val taskId = call.parameters["task_id"]
        controller.deleteTask(taskId)
        sendOk()
    }
}

const val TASK_CREATE_URL_PATH = "/tasks/create"
const val TASK_DELETE_URL_PATH = "/tasks/{task_id}/delete"
const val TASK_GET_URL_PATH = "/tasks/{task_id}"