package com.ecobenchmark.controllers;

import io.smallrye.mutiny.Uni;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/healthcheck")
public class Healthcheck {

    @GET
    public Uni<Response> healthcheck() {
        return Uni.createFrom().item(Response.noContent().build());
    }
}
