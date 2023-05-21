package com.ecobenchmark.controllers.addtask;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class TaskRequest {

    private String name;

    private String description;

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

}
