package com.ecobenchmark.controllers.getlists;


import io.vertx.mutiny.sqlclient.Row;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ListResponse {

    private UUID id;
    private String name;
    private List<TaskResponse> tasks = new ArrayList<>();
    private OffsetDateTime creationDate;
    private UUID accountId;

    public ListResponse(UUID id, String name, OffsetDateTime creationDate, UUID accountId) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.accountId = accountId;
    }
    
    public static ListResponse from(Row row){
        OffsetDateTime creationDate = row.get(OffsetDateTime.class, "creation_date");

        return new ListResponse(
        row.get(UUID.class,"id"),
        row.get(String.class,"name"),
                creationDate,
        row.get(UUID.class,"account_id"));
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

    public List<TaskResponse> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskResponse> tasks) {
        this.tasks = tasks;
    }

    public OffsetDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(OffsetDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
