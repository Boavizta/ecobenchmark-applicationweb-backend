package com.ecobenchmark.kotlinspringjpa.controllers.addaccount

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import java.util.*

data class AccountResponse(
    val id: UUID,
    val login: String,
    @JsonProperty("creation_date")
    val creationDate: Instant
)
