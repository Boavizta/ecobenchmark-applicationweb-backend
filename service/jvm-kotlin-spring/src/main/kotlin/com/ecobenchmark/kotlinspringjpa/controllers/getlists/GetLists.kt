package com.ecobenchmark.kotlinspringjpa.controllers.getlists


import com.ecobenchmark.kotlinspringjpa.repositories.AccountRepository
import com.ecobenchmark.kotlinspringjpa.repositories.ListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import java.util.*



@RestController
class GetLists {
    @Autowired
    lateinit var listRepository: ListRepository

    @GetMapping("/api/accounts/{id}/lists")
    fun getLists(@PathVariable(value = "id") accountId: UUID,@RequestParam(value = "page") page: Int): ResponseEntity<List<ListResponse>> {

        val pagination: Pageable = PageRequest.of(page, 10)

        val lists = listRepository.findAllByAccountId(accountId,pagination)

        val result = lists
            .map { l ->
                ListResponse(
                    l.id!!,
                    l.name,
                    l.tasks.map { t ->
                        TaskResponse(t.id!!, t.name, t.description, t.creationDate)
                    },
                    l.creationDate,
                    accountId
                )
            }

        return ResponseEntity.ok().body(result)
    }
}
