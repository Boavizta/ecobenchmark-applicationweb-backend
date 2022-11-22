package com.ecobenchmark.controllers.addtask;

import com.ecobenchmark.entities.ListEntity;
import com.ecobenchmark.entities.Task;
import com.ecobenchmark.repositories.ListEntityRepository;
import com.ecobenchmark.repositories.TaskRepository;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.UUID;

@Path("/api/lists")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AddTaskResource {

    @Inject
    private ListEntityRepository listEntityRepository;

    @Inject
    private TaskRepository taskRepository;

    @POST
    @Path("/{id}/tasks")
    @ReactiveTransactional
    public Uni<Response> addTask(@PathParam("id") UUID listId, TaskRequest taskRequest) {
        return listEntityRepository.findById(listId)
                .onItem().ifNotNull().transformToUni(listEntity -> createTask(listEntity, taskRequest))
                .onItem().ifNull().continueWith(this::listNotFound)
                .onFailure()
                .recoverWithItem(f-> Response.serverError().entity(f.getMessage()).build());
    }

    private Uni<Response> createTask(ListEntity listEntity, TaskRequest taskRequest) {
        Task task = new Task(taskRequest.getName(), taskRequest.getDescription(), Instant.now(),listEntity);
        return taskRepository.persist(task).onItem().transform(this::getTaskResponse);
    }

    private Response getTaskResponse(Task item) {
        TaskResponse taskResponse = new TaskResponse(item.getId(), item.getList().getId(), item.getName(), item.getDescription(), item.getCreationDate());
        return Response.ok().entity(taskResponse).build();
    }

    private Response listNotFound() {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}
