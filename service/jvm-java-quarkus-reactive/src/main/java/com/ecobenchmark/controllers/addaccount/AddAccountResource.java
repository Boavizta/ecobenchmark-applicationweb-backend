package com.ecobenchmark.controllers.addaccount;

import com.ecobenchmark.entities.Account;
import com.ecobenchmark.repositories.AccountRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.Instant;
import java.time.OffsetDateTime;

import static java.util.Collections.emptyList;

@Path("/api/accounts")
@ApplicationScoped
public class AddAccountResource {

    @Inject
    AccountRepository accountRepository;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @ReactiveTransactional
    public Uni<AccountResponse> addAccount(AccountRequest accountRequest) {
        Account account = new Account(accountRequest.getLogin(), Instant.now(), emptyList());
        return accountRepository.persist(account).onItem().transform(this::getAccountResponse);
    }

    private AccountResponse getAccountResponse(Account newAccount) {
        return new AccountResponse(newAccount.getId(), newAccount.getLogin(), newAccount.getCreationDate());
    }
}
