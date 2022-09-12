package com.ecobenchmark.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Account extends PanacheEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String login;

    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "account")
    private ListEntity lists;

    public Account(String login, LocalDateTime creationDate, ListEntity lists) {
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public ListEntity getLists() {
        return lists;
    }

    public void setLists(ListEntity lists) {
        this.lists = lists;
    }
}
