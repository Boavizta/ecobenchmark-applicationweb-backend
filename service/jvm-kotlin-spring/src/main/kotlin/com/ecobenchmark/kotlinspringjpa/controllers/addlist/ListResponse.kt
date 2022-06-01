package com.ecobenchmark.kotlinspringjpa.controllers.addlist

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.*

data class ListResponse(
    val id: UUID,
    val name: String,
    @JsonProperty("creation_date")
    val creationDate: Instant
)