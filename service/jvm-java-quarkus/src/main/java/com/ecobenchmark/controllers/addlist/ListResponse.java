package com.ecobenchmark.controllers.addlist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public class ListResponse {

    private UUID id;

    private String name;

    @JsonProperty("creation_date")
    Instant creationDate;

    public ListResponse(UUID id, String name, Instant creationDate) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
