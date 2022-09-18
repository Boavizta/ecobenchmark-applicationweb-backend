package com.ecobenchmark.controllers.addlist;

import com.ecobenchmark.entities.Account;
import com.ecobenchmark.entities.ListEntity;
import com.ecobenchmark.repositories.AccountRepository;
import com.ecobenchmark.repositories.ListEntityRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Collections;
import java.util.Optional;
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
    public Response addList(@PathParam("id") UUID accountId, ListRequest listRequest) {
        Optional<Account> account = accountRepository.findByIdOptional(accountId);
        if(account.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        ListEntity listEntity = new ListEntity(account.get(), listRequest.getName(), Instant.now(), Collections.emptyList());
        listEntityRepository.persist(listEntity);
        return Response.ok().entity(new ListResponse(listEntity.getId(), listEntity.getName(), listEntity.getCreationDate())).build();
    }
}
