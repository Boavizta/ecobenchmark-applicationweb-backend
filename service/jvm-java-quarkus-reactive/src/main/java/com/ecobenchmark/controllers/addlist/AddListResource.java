package com.ecobenchmark.controllers.addlist;

import com.ecobenchmark.entities.Account;
import com.ecobenchmark.entities.ListEntity;
import com.ecobenchmark.repositories.AccountRepository;
import com.ecobenchmark.repositories.ListEntityRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Collections;
import java.util.UUID;

@Path("/api/accounts")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AddListResource {

    @Inject
    private ListEntityRepository listEntityRepository;

    @Inject
    private AccountRepository accountRepository;

    @POST
    @Path("/{id}/lists")
    @ReactiveTransactional
    public Uni<Response> addList(@PathParam("id") UUID accountId, ListRequest listRequest) {
        return accountRepository
                .findById(accountId)
                .onItem().ifNotNull().transformToUni(account -> getResponseUni(listRequest, account))
                .onItem().ifNull().continueWith(Response.status(Response.Status.BAD_REQUEST).build())
                .onFailure().recoverWithItem(f-> Response.serverError().entity(f.getMessage()).build());
    }

    private Uni<Response> getResponseUni(ListRequest listRequest, Account account) {
        ListEntity listEntity = new ListEntity(account, listRequest.getName(), Instant.now(), Collections.emptyList());
        return listEntityRepository.persist(listEntity).onItem().transform(this::getResponse);
    }

    private Response getResponse(ListEntity listEntity) {
        ListResponse listResponse = new ListResponse(listEntity.getId(), listEntity.getName(), listEntity.getCreationDate());
        return Response.ok().entity(listResponse).build();
    }
}
