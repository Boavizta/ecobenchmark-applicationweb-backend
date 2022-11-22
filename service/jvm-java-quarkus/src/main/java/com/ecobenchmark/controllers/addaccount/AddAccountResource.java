package com.ecobenchmark.controllers.addaccount;

import com.ecobenchmark.entities.Account;
import com.ecobenchmark.repositories.AccountRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;

import static java.util.Collections.emptyList;

@Path("/api/accounts")
@ApplicationScoped
public class AddAccountResource {

    @Inject
    private AccountRepository accountRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public AccountResponse addAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest.getLogin(), Instant.now(), emptyList());
        accountRepository.persist(account);

        return new AccountResponse(account.getId(), account.getLogin(), account.getCreationDate());
    }
}
