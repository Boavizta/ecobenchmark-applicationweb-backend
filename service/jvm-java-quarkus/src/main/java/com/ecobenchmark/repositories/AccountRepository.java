package com.ecobenchmark.repositories;

import com.ecobenchmark.entities.Account;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class AccountRepository implements PanacheRepository<Account> {

    public Optional<Account> findByIdOptional(UUID accountId) {
        return find("id", accountId).firstResultOptional();
    }
}
