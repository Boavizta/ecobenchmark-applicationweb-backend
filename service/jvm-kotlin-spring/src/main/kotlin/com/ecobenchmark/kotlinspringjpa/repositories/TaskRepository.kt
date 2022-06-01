package com.ecobenchmark.kotlinspringjpa.repositories

import com.ecobenchmark.kotlinspringjpa.entities.Task
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface TaskRepository : CrudRepository<Task, UUID> {
}