package com.ecobenchmark.repositories;

import com.ecobenchmark.entities.ListEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ListEntityRepository implements PanacheRepository<ListEntity> {
    public Optional<ListEntity> findByIdOptional(UUID listId) {
        return find("id", listId).firstResultOptional();
    }
}
