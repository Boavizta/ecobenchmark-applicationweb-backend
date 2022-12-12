package com.ecobenchmark.controllers.getlists;

import io.vertx.mutiny.sqlclient.Row;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class TaskResponse {

    private UUID id;

    private String name;

    private String description;

    private Instant creationDate;

    public TaskResponse(UUID id, String name, String description, Instant creationDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public static TaskResponse from(Row row){
        LocalDateTime creationDate = row.get(LocalDateTime.class, "task_creation_date");
        Instant creationDateInstant = creationDate != null ? creationDate.toInstant(ZoneOffset.UTC) : null;
        return new TaskResponse(
                row.get(UUID.class,"task_id"),
                row.get(String.class,"task_name"),
                row.get(String.class,"task_description"),
                creationDateInstant);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
