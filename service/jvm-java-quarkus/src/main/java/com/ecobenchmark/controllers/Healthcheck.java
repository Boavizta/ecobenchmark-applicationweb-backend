package com.ecobenchmark.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/healthcheck")
public class Healthcheck {

    @GET
    public Response healthcheck() {
        return Response.noContent().build();
    }
}
