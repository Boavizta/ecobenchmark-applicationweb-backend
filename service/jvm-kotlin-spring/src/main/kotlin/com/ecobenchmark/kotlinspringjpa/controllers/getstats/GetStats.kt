package com.ecobenchmark.kotlinspringjpa.controllers.getstats

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.ResultSet
import java.util.*

@RestController
class GetStats {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @GetMapping("/api/stats")
    fun getStats(): List<StatsResponse> {
        val stats = jdbcTemplate.query<Any>(
            """
                SELECT 
				id,  
				login, 
				count(list_id) AS nb_list, 
				avg(nb_tasks) AS avg_tasks 
			FROM (
				SELECT 
					account.id, 
					account.login, 
					list.id list_id, 
					count(task.id) nb_tasks 
				FROM account
				INNER JOIN list ON (list.account_id=account.id) 
				LEFT JOIN task ON (task.list_id=list.id) 
				GROUP BY account.id, account.login, list.id
			) t 
			GROUP BY id, login""".trimIndent()
        ) { rs: ResultSet, _: Int ->
            StatsResponse(
                UUID.fromString(rs.getString("id")),
                rs.getString("login"),
                rs.getInt("nb_list"),
                rs.getFloat("avg_tasks")
            )
        } as List<StatsResponse>

        return stats
    }
}
