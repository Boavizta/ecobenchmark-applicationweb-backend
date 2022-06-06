package com.ecobenchmark.kotlinspringjpa.controllers.getlists

import java.time.Instant
import java.util.UUID

data class ListResponse(
    val id: UUID,
    val name: String,
    val tasks: MutableList<TaskResponse>,
    val creationDate: Instant,
    val accountId: UUID
)
