package com.ecobenchmark.controllers.getlists;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.Timestamp;
import java.time.Instant;
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
    @Transactional
    public Response getLists(@PathParam("id") UUID accountId, @QueryParam("page") int page) {

        Query nativeQuery = entityManager.createNativeQuery("""
                SELECT
                    CAST(l.id AS VARCHAR),
                    l.name,
                    l.creation_date,
                    CAST(l.account_id AS VARCHAR),
                    CAST(t.id AS VARCHAR) AS task_id,
                    t.name AS task_name,
                    t.description AS task_description,
                    t.creation_date AS task_creation_date
                FROM list l
                    LEFT JOIN task t ON l.id = t.list_id
                WHERE
                    l.account_id = :accountId
                    AND l.id IN (SELECT id FROM list WHERE account_id = :accountId LIMIT :nbElement OFFSET :offset)""".trim(), Tuple.class);
        List<Tuple> resultList = nativeQuery
                .setParameter("accountId", accountId)
                .setParameter("nbElement", 10)
                .setParameter("offset", page * 10)
                .getResultList();

        Map<UUID, ListResponse> listResponseMap = new LinkedHashMap<>();

        for(Tuple tuple : resultList){
            UUID listId = UUID.fromString(tuple.get("id", String.class));
            ListResponse list = listResponseMap.computeIfAbsent(
                    listId,
                    id -> contructListResponse(tuple)
            );

            list.getTasks().add(
                    contructTaskResponse(tuple)
            );

        }

        return Response.ok(listResponseMap.values()).build();
    }

    private ListResponse contructListResponse(Tuple tuple) {
        Timestamp creationDate = tuple.get("creation_date", Timestamp.class);
        Instant creationDateInstant = creationDate != null ? creationDate.toInstant(): null;
        return new ListResponse(UUID.fromString(tuple.get("id", String.class)), tuple.get("name", String.class), creationDateInstant, UUID.fromString(tuple.get("account_id", String.class)));
    }

    private TaskResponse contructTaskResponse(Tuple tuple) {
        Timestamp taskCreationDate = tuple.get("task_creation_date", Timestamp.class);
        Instant creationDate = taskCreationDate.toInstant() != null ? taskCreationDate.toInstant() : null;
        return new TaskResponse(UUID.fromString(tuple.get("task_id", String.class)), tuple.get("task_name", String.class), tuple.get("task_description", String.class), creationDate);
    }
}
