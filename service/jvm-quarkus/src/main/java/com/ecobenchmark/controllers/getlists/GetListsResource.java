package com.ecobenchmark.controllers.getlists;

import org.hibernate.query.Query;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;
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
    public Response getLists(@PathParam("id") UUID accountId, @QueryParam("page") int page) {

        List<ListResponse> resultList = entityManager.createNativeQuery("""
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
                            AND l.id IN (SELECT id FROM list WHERE account_id = :accountId LIMIT :nbElement OFFSET :offset)""".trim(), ListResponse.class)
                .setParameter("accountId", accountId)
                .setParameter("nbElement", 10)
                .setParameter("offset", page * 10)
                .unwrap(Query.class)
                .setResultTransformer(new ListResponseTransformer())
                .getResultList();

        return Response.ok(resultList).build();
    }
}
