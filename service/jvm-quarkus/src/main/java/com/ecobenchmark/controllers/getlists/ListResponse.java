package com.ecobenchmark.controllers.getlists;



import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ListResponse {

    private UUID id;
    private String name;
    private List<TaskResponse> tasks;
    private Instant creationDate;
    private UUID accountId;

    public ListResponse(UUID id, String name, Instant creationDate, UUID accountId) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.accountId = accountId;
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

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }
}
