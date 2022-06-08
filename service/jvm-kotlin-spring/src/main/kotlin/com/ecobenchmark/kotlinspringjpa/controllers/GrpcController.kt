package com.ecobenchmark.kotlinspringjpa.controllers

import com.ecobenchmark.kotlinspringjpa.controllers.getlists.ListResponse
import com.ecobenchmark.kotlinspringjpa.controllers.getlists.TaskResponse
import com.ecobenchmark.kotlinspringjpa.entities.Account
import com.ecobenchmark.kotlinspringjpa.entities.ListEntity
import com.ecobenchmark.kotlinspringjpa.entities.Task
import com.ecobenchmark.kotlinspringjpa.repositories.AccountRepository
import com.ecobenchmark.kotlinspringjpa.repositories.ListRepository
import com.ecobenchmark.kotlinspringjpa.repositories.TaskRepository
import com.google.protobuf.Empty
import io.grpc.Status.ABORTED
import io.grpc.StatusException
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.sql.ResultSet
import java.time.Instant
import java.util.*



@Service
@Controller
@Repository
@GRpcService
class GrpcController:ecobenchmark.TodoListGrpcKt.TodoListCoroutineImplBase() {

    @Autowired
    lateinit var listRepository: ListRepository

    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var taskRepository: TaskRepository

    @Autowired
    lateinit var serviceLayer: ServiceLayer


    override suspend fun addAccount(request: ecobenchmark.Endpoints.addAccountInput):
            ecobenchmark.Endpoints.addAccountOutput  {
        val account = Account(request.getLogin(), Instant.now(), emptyList())
        accountRepository.save(account)
        return ecobenchmark.Endpoints.addAccountOutput.newBuilder()
            .setId(account.id.toString())
            .setLogin(account.login)
            .setCreationDate(com.google.protobuf.Timestamp.newBuilder().setSeconds(account.creationDate.epochSecond).build())
            .build()
    }

    override suspend fun addListToAccount(request: ecobenchmark.Endpoints.addListInput):
            ecobenchmark.Endpoints.addListOutput {
        val account = accountRepository.findById(UUID.fromString(request.getAccountId()))
        if (account.isEmpty) {
            throw StatusException(ABORTED)
        }

        val list = ListEntity(account.get(), request.getName(), Instant.now(), emptyList())
        listRepository.save(list)
        return ecobenchmark.Endpoints.addListOutput.newBuilder()
            .setId(list.id.toString())
            .setName(list.name)
            .setCreationDate(com.google.protobuf.Timestamp.newBuilder().setSeconds(list.creationDate.epochSecond).build())
            .build()
    }

    override suspend fun addTaskToList(request: ecobenchmark.Endpoints.addTaskInput): ecobenchmark.Endpoints.addTaskOutput {
        val list = listRepository.findById(UUID.fromString(request.getListId()))
        if (list.isEmpty) {
            throw StatusException(ABORTED)
        }

        val task = Task(list.get(), request.getName(), request.getDescription(), Instant.now())
        taskRepository.save(task)
        return  ecobenchmark.Endpoints.addTaskOutput.newBuilder()
            .setId(task.id.toString())
            .setName(task.name)
            .setDescription(task.description)
            .setListId(task.list.id.toString())
            .setCreationDate(com.google.protobuf.Timestamp.newBuilder().setSeconds(task.creationDate.epochSecond).build())
            .build()
    }

    override suspend fun getListsWithTasks(request: ecobenchmark.Endpoints.getListInput):
            ecobenchmark.Endpoints.getListOutput {

        val accountid= request.getAccountId()
        val page= request.getPage()

        val listResponseMap = serviceLayer.getListWithtasks(accountid, page)

        val out =ecobenchmark.Endpoints.getListOutput.newBuilder();

        listResponseMap.values.forEach{ itl ->
            val list=ecobenchmark.Endpoints.List.newBuilder()
                .setId(itl.id.toString())
                .setName(itl.name)
                .setAccountId(itl.accountId.toString())
                .setCreationDate(com.google.protobuf.Timestamp.newBuilder().setSeconds(itl.creationDate.epochSecond).build())

            itl.tasks.forEach{ itt ->
                list.addTasks(ecobenchmark.Endpoints.Task.newBuilder()
                    .setId(itt.id.toString())
                    .setName(itt.name)
                    .setDescription(itt.description)
                    .setCreationDate(com.google.protobuf.Timestamp.newBuilder().setSeconds(itt.creationDate.epochSecond).build())
                    .build()
                )
            }

            list.build()
            out.addList(list)
        }

        return out.build()
    }


    override suspend fun getStats(request: Empty): ecobenchmark.Endpoints.getStatsOutput {
        val stats = serviceLayer.getStats()

        val out = ecobenchmark.Endpoints.getStatsOutput.newBuilder();

        stats.forEach {
            out.addStats(it)
        }

        return out.build()
    }
}
