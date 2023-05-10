package com.ecobenchmark.controllers.addlist;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public class ListRequest {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
