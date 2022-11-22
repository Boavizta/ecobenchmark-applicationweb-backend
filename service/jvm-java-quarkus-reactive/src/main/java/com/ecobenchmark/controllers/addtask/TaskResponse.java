package com.ecobenchmark.controllers.addtask;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public class TaskResponse {

    private UUID id;

    @JsonProperty("list_id")
    private UUID listId;

    private String name;

    private String description;

    @JsonProperty("creation_date")
    private Instant creationDate;

    public TaskResponse(UUID id, UUID listId, String name, String description, Instant creationDate) {
        this.id = id;
        this.listId = listId;
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getListId() {
        return listId;
    }

    public void setListId(UUID listId) {
        this.listId = listId;
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
