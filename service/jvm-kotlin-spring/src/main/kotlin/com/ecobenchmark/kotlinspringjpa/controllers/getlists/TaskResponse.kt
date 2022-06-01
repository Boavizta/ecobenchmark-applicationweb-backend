package com.ecobenchmark.kotlinspringjpa.controllers.getlists

import java.time.Instant
import java.util.*

data class TaskResponse(
    val id: UUID,
    val name: String,
    val description: String,
    val creationDate: Instant
)
