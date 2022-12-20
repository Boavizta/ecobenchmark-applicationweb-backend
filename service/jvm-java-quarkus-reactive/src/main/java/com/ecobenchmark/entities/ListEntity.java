package com.ecobenchmark.entities;


import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity(name = "list")
public class ListEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    private Account account;

    private String name;

    @Type(type = "com.ecobenchmark.entities.TimestampWithTimezone")
    @Column(name = "creation_date", columnDefinition = "timestamp with time zone not null")
    private Instant creationDate;

    @OneToMany(mappedBy = "list")
    private List<Task> tasks;

    public ListEntity(Account account, String name, Instant creationDate, List<Task> tasks) {
        this.account = account;
        this.name = name;
        this.creationDate = creationDate;
        this.tasks = tasks;
    }

    public ListEntity() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}
