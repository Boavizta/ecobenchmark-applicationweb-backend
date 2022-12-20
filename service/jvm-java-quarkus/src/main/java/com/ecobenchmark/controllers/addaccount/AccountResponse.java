package com.ecobenchmark.controllers.addaccount;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.UUID;

public class AccountResponse {

    private UUID id;

    private String login;

    @JsonProperty("creation_date")
    private Instant creationDate;

    public AccountResponse(UUID id, String login, Instant creationDate) {
        this.id = id;
        this.login = login;
        this.creationDate = creationDate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }
}
