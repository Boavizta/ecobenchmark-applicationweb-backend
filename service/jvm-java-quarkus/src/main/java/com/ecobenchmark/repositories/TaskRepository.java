package com.ecobenchmark.repositories;

import com.ecobenchmark.entities.Task;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TaskRepository implements PanacheRepository<Task> {
}
