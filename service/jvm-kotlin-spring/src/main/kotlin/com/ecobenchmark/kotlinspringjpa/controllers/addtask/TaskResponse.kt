package com.ecobenchmark.kotlinspringjpa.controllers.addtask

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.UUID

data class TaskResponse(
    val id: UUID,
    @JsonProperty("list_id")
    val listId: UUID,
    val name: String,
    val description: String,
    @JsonProperty("creation_date")
    val creationDate: Instant
)