package com.ecobenchmark.kotlinspringjpa.controllers.getstats

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.ResultSet
import java.util.*
import kotlin.math.round

@RestController
class GetStats {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @GetMapping("/api/stats")
    fun getStats(): List<StatsResponse> {
        val preparedData = mutableMapOf<UUID, PreparedData>()

        jdbcTemplate.query<Any>(
            """
            SELECT account.id account_id, account.login, list.id list_id, task.id task_id
            FROM account
                INNER JOIN list ON (list.account_id=account.id)
                LEFT JOIN task ON (task.list_id=list.id)""".trimIndent()
        ) { rs: ResultSet, _: Int ->
            val accountId = UUID.fromString(rs.getString("account_id"))
            val listId = UUID.fromString(rs.getString("list_id"))
            val taskId = rs.getString("task_id")

            var prepData = preparedData[accountId]
            if (prepData == null) {
                prepData = PreparedData(
                    accountId,
                    rs.getString("login"),
                    mutableMapOf(listId to 0),
                    0
                )
                if (taskId != null) {
                    prepData.taskIdMap[listId] = 1
                    prepData.tasksCount = 1
                }
                preparedData[accountId] = prepData
            } else {
                if (prepData.taskIdMap[listId] == null) {
                    prepData.taskIdMap[listId] = 0
                }
                if (taskId != null) {
                    prepData.taskIdMap[listId] = prepData.taskIdMap[listId]!!.plus(1)
                    prepData.tasksCount++
                }
            }
        }

        return preparedData.map { (_, data) ->
            val avgTask = data.tasksCount.toFloat().div(data.taskIdMap.count())
            StatsResponse(
                data.accountId,
                data.accountLogin,
                data.taskIdMap.count(),
                round(avgTask.times(100).toFloat()).div(100)
            )
        }.toList()
    }
}
