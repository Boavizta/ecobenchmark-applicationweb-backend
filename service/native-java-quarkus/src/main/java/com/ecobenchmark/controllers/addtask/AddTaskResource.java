package com.ecobenchmark.controllers.addtask;

import com.ecobenchmark.entities.ListEntity;
import com.ecobenchmark.entities.Task;
import com.ecobenchmark.repositories.ListEntityRepository;
import com.ecobenchmark.repositories.TaskRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.time.Instant;
import java.util.Optional;
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
    @Transactional
    public Response addTask(@PathParam("id") UUID listId, TaskRequest taskRequest) {
        Optional<ListEntity> list = listEntityRepository.findByIdOptional(listId);
        if(list.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Task task = new Task(taskRequest.getName(), taskRequest.getDescription(), Instant.now(),list.get());
        taskRepository.persist(task);
        return Response.ok().entity(new TaskResponse(task.getId(), task.getList().getId(), task.getName(), task.getDescription(), task.getCreationDate())).build();
    }
}
