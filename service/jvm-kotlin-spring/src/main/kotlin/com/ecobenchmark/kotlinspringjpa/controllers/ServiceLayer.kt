package com.ecobenchmark.kotlinspringjpa.controllers

import com.ecobenchmark.kotlinspringjpa.controllers.getlists.ListResponse
import com.ecobenchmark.kotlinspringjpa.controllers.getlists.TaskResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController
import java.sql.ResultSet
import java.util.*

@Service
class ServiceLayer {


    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate


    fun getListWithtasks(accountid: String, page: Int): Map<UUID, ListResponse> {

        val listResponseMap = mutableMapOf<UUID, ListResponse>()

    jdbcTemplate.query<Any>(
        """
                SELECT
                l.id,
                l.name,
                l.creation_date,
                l.account_id,
                t.id AS task_id,
                t.name AS task_name,
                t.description,
                t.creation_date AS task_creation_date
            FROM list l
                LEFT JOIN task t ON l.id = t.list_id
            WHERE
                l.account_id = ?
                AND l.id IN (SELECT id FROM list WHERE account_id = ? LIMIT ? OFFSET ?)""".trimIndent(),
        { rs: ResultSet, _: Int ->
            val listId = UUID.fromString(rs.getString("id"))
            var listResponse = listResponseMap[listId]

            if (listResponse == null) {
                listResponse = ListResponse(
                    listId,
                    rs.getString("name"),
                    mutableListOf(),
                    rs.getTimestamp("creation_date").toInstant(),
                    UUID.fromString(accountid)
                )
                listResponseMap[listId] = listResponse
            }

            if (rs.getString("task_id") != null) {
                listResponse.tasks.add(
                    TaskResponse(
                        UUID.fromString(rs.getString("task_id")),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getTimestamp("task_creation_date").toInstant()
                    )
                )
            }
        },
        UUID.fromString(accountid),
            UUID.fromString(accountid),
        10,
        page * 10
    )
        return listResponseMap
    }


    fun getStats():List<ecobenchmark.Endpoints.Stats> {
        return jdbcTemplate.query<Any>(
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
            ecobenchmark.Endpoints.Stats.newBuilder()
                .setAccountId(rs.getString("id"))
                .setAccountLogin(rs.getString("login"))
                .setListCount(rs.getLong("nb_list"))
                .setTaskAvg(rs.getDouble("avg_tasks"))
                .build()
        } as List<ecobenchmark.Endpoints.Stats>
    }

}