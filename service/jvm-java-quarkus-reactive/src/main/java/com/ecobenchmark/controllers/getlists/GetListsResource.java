package com.ecobenchmark.controllers.getlists;

import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/api/accounts")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class GetListsResource {

    @Inject
    private EntityManager entityManager;

    @GET
    @Path("/{id}/lists")
    @ReactiveTransactional
    public Uni<Response> getLists(@PathParam("id") UUID accountId, @QueryParam("page") int page) {

        Query nativeQuery = entityManager.createNativeQuery("""
                SELECT
                    l.id,
                    l.name,
                    l.creation_date,
                    l.account_id,
                    t.id AS task_id,
                    t.name AS task_name,
                    t.description AS task_description,
                    t.creation_date AS task_creation_date
                FROM list l
                    LEFT JOIN task t ON l.id = t.list_id
                WHERE
                    l.account_id = :accountId
                    AND l.id IN (SELECT id FROM list WHERE account_id = :accountId LIMIT :nbElement OFFSET :offset)""".trim(), "getListsResponseMapping");
        List<Object[]> resultList = nativeQuery
                .setParameter("accountId", accountId)
                .setParameter("nbElement", 10)
                .setParameter("offset", page * 10)
                .getResultList();

        Map<UUID, ListResponse> listResponseMap = new LinkedHashMap<>();

        for(Object[] objects : resultList){
            ListResponse listResponse = (ListResponse) objects[0];

            ListResponse mappedList = listResponseMap.putIfAbsent(listResponse.getId(), listResponse);

            if(mappedList != null){
                TaskResponse taskResponse = (TaskResponse) objects[1];
                List<TaskResponse> tasks = mappedList.getTasks();
                tasks.add(taskResponse);
            }

        }

        return Uni.createFrom().item(Response.ok(listResponseMap.values()).build());
    }


}
