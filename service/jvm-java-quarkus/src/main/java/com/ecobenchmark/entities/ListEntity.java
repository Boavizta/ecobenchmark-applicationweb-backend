package com.ecobenchmark.entities;


import com.ecobenchmark.controllers.getlists.ListResponse;
import com.ecobenchmark.controllers.getlists.TaskResponse;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity(name = "list")
@SqlResultSetMapping(
        name = "getListsResponseMapping",
        classes = {
                @ConstructorResult(
                        targetClass = ListResponse.class,
                        columns = {
                                @ColumnResult(name = "id" ,type = UUID.class),
                                @ColumnResult(name = "name", type = String.class),
                                @ColumnResult(name = "creation_date", type = Instant.class),
                                @ColumnResult(name = "account_id", type = UUID.class)}),
                @ConstructorResult(
                        targetClass = TaskResponse.class,
                        columns = {
                                @ColumnResult(name = "task_id" ,type = UUID.class),
                                @ColumnResult(name = "task_name", type = String.class),
                                @ColumnResult(name = "task_description", type = String.class),
                                @ColumnResult(name = "task_creation_date", type = Instant.class)})
        })
public class ListEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @ManyToOne
    private Account account;

    private String name;

    @Column(name = "creation_date")
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
