package com.ecobenchmark.entities;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
public class Account {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String login;

    @Type(type = "com.ecobenchmark.entities.TimestampWithTimezone")
    @Column(name = "creation_date", columnDefinition = "timestamp with time zone not null")
    private Instant creationDate;

    @OneToMany(mappedBy = "account")
    private List<ListEntity> lists;

    public Account() {
    }

    public Account(String login, Instant creationDate, List<ListEntity> lists) {
        this.login = login;
        this.creationDate = creationDate;
        this.lists = lists;
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

    public List<ListEntity> getLists() {
        return lists;
    }

    public void setLists(List<ListEntity> lists) {
        this.lists = lists;
    }
}
