package com.ecobenchmark.controllers.getlists;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
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
    PgPool client;

    @GET
    @Path("/{id}/lists")
    public Multi<ListResponse> getLists(@PathParam("id") UUID accountId, @QueryParam("page") int page) {

        return client.preparedQuery("""
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
                            l.account_id = $1
                            AND l.id IN (SELECT id FROM list WHERE account_id = $1 LIMIT $2 OFFSET $3)""".trim())
                .execute(Tuple.of(accountId, 10, page * 10))
                .onItem().transformToMulti(rowSet -> {
                    Map<UUID, ListResponse> listResponseMap = new LinkedHashMap<>();

                    for (Row row : rowSet) {
                        ListResponse listResponse = ListResponse.from(row);

                        ListResponse mappedList = listResponseMap.putIfAbsent(listResponse.getId(), listResponse);

                        if (mappedList != null) {
                            TaskResponse taskResponse = TaskResponse.from(row);
                            List<TaskResponse> tasks = mappedList.getTasks();
                            tasks.add(taskResponse);
                        }

                    }
                    return Multi.createFrom().iterable(listResponseMap.values()).onFailure().invoke(t -> Log.error(
                            "Oh no! We received a failure: " + t.getMessage(), t)
                    );
                });
    }


}
