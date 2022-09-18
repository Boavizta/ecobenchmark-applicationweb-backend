package com.ecobenchmark.controllers.addaccount;

import com.ecobenchmark.entities.Account;
import com.ecobenchmark.repositories.AccountRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.time.Instant;

import static java.util.Collections.emptyList;

@Path("/api/accounts")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AddAccountResource {

    @Inject
    private AccountRepository accountRepository;

    @POST
    public Response addAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest.getLogin(), Instant.now(), emptyList());
        accountRepository.persist(account);

        return Response.ok(new AccountResponse(account.getId(), account.getLogin(), account.getCreationDate())).build();
    }
}
