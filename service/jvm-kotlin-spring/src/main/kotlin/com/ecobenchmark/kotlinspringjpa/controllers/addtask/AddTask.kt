package com.ecobenchmark.kotlinspringjpa.controllers.addtask

import com.ecobenchmark.kotlinspringjpa.entities.Task
import com.ecobenchmark.kotlinspringjpa.repositories.ListRepository
import com.ecobenchmark.kotlinspringjpa.repositories.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class AddTask {
    @Autowired
    lateinit var listRepository: ListRepository

    @Autowired
    lateinit var taskRepository: TaskRepository

    @PostMapping("/api/lists/{id}/tasks")
    fun addTask(@PathVariable(value = "id") listId: UUID, @RequestBody taskRequest: TaskRequest): ResponseEntity<TaskResponse> {
        val list = listRepository.findById(listId)
        if (list.isEmpty) {
            return ResponseEntity.badRequest().build()
        }

        val task = Task(list.get(), taskRequest.name, taskRequest.description, Instant.now())
        taskRepository.save(task)
        return ResponseEntity.ok().body(TaskResponse(task.id!!, task.list.id!!, task.name, task.description, task.creationDate))
    }
}
