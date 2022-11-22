package com.ecobenchmark.repositories;

import com.ecobenchmark.entities.Account;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {

    public Uni<Account> findById(UUID accountId) {
        return find("id", accountId).firstResult();
    }
}
