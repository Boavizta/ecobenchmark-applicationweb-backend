package com.ecobenchmark.controllers.getstats;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Path("/api/stats")
@ApplicationScoped
@Produces("application/json")
public class GetStatsResource {

    @Inject
    private EntityManager entityManager;

    @GET
    public List<StatsResponse> getStats(){
        Query nativeQuery = entityManager.createQuery("""
            SELECT
                a.id,
                a.login,
                count(list_id) AS nb_list,
                avg(nb_tasks) AS avg_tasks
            FROM (
                SELECT
                    account.id,
                    account.login,
                    list.id AS list_id,
                    count(task.id) AS nb_tasks
                FROM Account a
                INNER JOIN a.lists as list
                LEFT JOIN list.tasks as task
                GROUP BY a.id, a.login, list.id
            ) t
            GROUP BY a.id, a.login""".trim(), Tuple.class);
        List<Tuple> resultList = nativeQuery.getResultList();
        List<StatsResponse> responseList = new ArrayList<>();
        for(Tuple tuple : resultList){
            StatsResponse statsResponse = new StatsResponse(UUID.fromString(tuple.get("id", String.class)), tuple.get("login", String.class), tuple.get("nb_list", Integer.class), tuple.get("avg_tasks", Float.class));
            responseList.add(statsResponse);
        }
        return responseList;
    }
}
