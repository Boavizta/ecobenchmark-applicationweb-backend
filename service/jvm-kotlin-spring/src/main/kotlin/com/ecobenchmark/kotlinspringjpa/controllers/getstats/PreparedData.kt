package com.ecobenchmark.kotlinspringjpa.controllers.getstats

import java.util.UUID

data class PreparedData(
    val accountId: UUID,
    val accountLogin: String,
    val taskIdMap: MutableMap<UUID, Int>,
    var tasksCount: Int
)
