package com.ecobenchmark.kotlinspringjpa.controllers.getstats

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

data class StatsResponse(
    @JsonProperty("account_id")
    val accountId: UUID,
    @JsonProperty("account_login")
    val accountLogin: String,
    @JsonProperty("list_count")
    val listCount: Int,
    @JsonProperty("task_avg")
    val taskAvg: Float
)
