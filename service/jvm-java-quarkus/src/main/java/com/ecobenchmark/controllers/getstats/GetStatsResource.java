package com.ecobenchmark.controllers.getstats;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/api/stats")
@ApplicationScoped
@Produces("application/json")
public class GetStatsResource {

    @Inject
    private EntityManager entityManager;

    @GET
    public List<StatsResponse> getStats(){
        Query nativeQuery = entityManager.createNativeQuery("""
            SELECT
                id,
                login,
                count(list_id) AS nb_list,
                avg(nb_tasks) AS avg_tasks
            FROM (
                SELECT
                    account.id,
                    account.login,
                    list.id as list_id,
                    count(task.id) as nb_tasks
                FROM account
                INNER JOIN list ON (list.account_id=account.id)
                LEFT JOIN task ON (task.list_id=list.id)
                GROUP BY account.id, account.login, list.id
            ) t
            GROUP BY id, login""".trim(), "StatsResponseMapping");

        return nativeQuery.getResultList();

    }
}
