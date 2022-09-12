package com.ecobenchmark.controllers;

import com.ecobenchmark.entities.Account;

@Path("/api/accounts")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AccountResource {

    @Post
    public AccountResponse addAccount(@RequestBody accountRequest: AccountRequest): AccountResponse {
        Account account = Account(accountRequest.login, Instant.now(), emptyList())
        account.persist();
        return AccountResponse(account.id, account.login, account.creationDate);
    }
}
