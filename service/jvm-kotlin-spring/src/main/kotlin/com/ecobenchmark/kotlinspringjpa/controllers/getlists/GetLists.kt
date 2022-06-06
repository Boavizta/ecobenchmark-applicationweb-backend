package com.ecobenchmark.kotlinspringjpa.controllers.getlists

import com.ecobenchmark.kotlinspringjpa.controllers.getstats.StatsResponse
import com.ecobenchmark.kotlinspringjpa.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.ResultSetExtractor
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.sql.ResultSet
import java.time.Instant
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.MutableMap as MutableMap


@RestController
class GetLists {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @GetMapping("/api/accounts/{id}/lists")
    fun getLists(@PathVariable(value = "id") accountId: UUID, @RequestParam(defaultValue = "0") page: Int): ResponseEntity<List<ListResponse>> {
        val listResponseMap = mutableMapOf<UUID, ListResponse>()

        val stats = jdbcTemplate.query<Any>(
            """
                SELECT
                BIN_TO_UUID(l.id) as id,
                l.name,
                l.creation_date,
                BIN_TO_UUID(l.account_id) as account_id,
                BIN_TO_UUID(t.id) AS task_id,
                t.name AS task_name,
                t.description,
                t.creation_date AS task_creation_date
            FROM list l
                LEFT JOIN task t ON l.id = t.list_id
            WHERE
                l.account_id = UUID_TO_BIN(?)
                AND l.id IN (select id from (SELECT id FROM list WHERE account_id = UUID_TO_BIN(?) LIMIT ?,?) tmp)""".trimIndent(),
            { rs: ResultSet, _: Int ->
                val listId = UUID.fromString(rs.getString("id"))
                var listResponse = listResponseMap[listId]

                if (listResponse == null) {
                    listResponse = ListResponse(
                        listId,
                        rs.getString("name"),
                        mutableListOf(),
                        rs.getTimestamp("creation_date").toInstant(),
                        accountId
                    )
                    listResponseMap[listId] = listResponse
                }

                if (rs.getString("task_id") != null) {
                    listResponse.tasks.add(
                        TaskResponse(
                            UUID.fromString(rs.getString("task_id")),
                            rs.getString("task_name"),
                            rs.getString("description"),
                            rs.getTimestamp("task_creation_date").toInstant()
                        )
                    )
                }
            },
            accountId.toString(),
            accountId.toString(),
            page * 10,
            10
        )

        return ResponseEntity.ok().body(listResponseMap.values.toList())
    }
}
