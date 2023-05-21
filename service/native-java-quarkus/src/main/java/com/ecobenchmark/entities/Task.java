package com.ecobenchmark.entities;

import com.ecobenchmark.controllers.getstats.StatsResponse;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@SqlResultSetMapping(
        name = "StatsResponseMapping",
        classes = {
                @ConstructorResult(
                        targetClass = StatsResponse.class,
                        columns = {
                                @ColumnResult(name = "id" ,type = UUID.class),
                                @ColumnResult(name = "login", type = String.class),
                                @ColumnResult(name = "nb_list", type = Integer.class),
                                @ColumnResult(name = "avg_tasks", type = Float.class)})})
@Entity
public class Task {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    private String name;

    private String description;

    @Column(name = "creation_date")
    private Instant creationDate;

    @ManyToOne
    private ListEntity list;

    public Task(String name, String description, Instant creationDate, ListEntity list) {
        this.name = name;
        this.description = description;
        this.creationDate = creationDate;
        this.list = list;
    }

    public Task() {
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

    public ListEntity getList() {
        return list;
    }

    public void setList(ListEntity list) {
        this.list = list;
    }
}
