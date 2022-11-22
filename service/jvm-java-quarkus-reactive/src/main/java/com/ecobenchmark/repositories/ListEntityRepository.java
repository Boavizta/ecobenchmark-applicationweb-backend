package com.ecobenchmark.repositories;

import com.ecobenchmark.entities.ListEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import java.util.UUID;

@ApplicationScoped
public class ListEntityRepository implements PanacheRepository<ListEntity> {
    public Uni<ListEntity> findById(UUID listId) {
        return find("id", listId).firstResult();
    }
}
