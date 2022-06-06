package com.ecobenchmark.kotlinspringjpa.controllers.getlists


import com.ecobenchmark.kotlinspringjpa.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

import java.util.*



@RestController
class GetLists {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @GetMapping("/api/accounts/{id}/lists")
    fun getLists(@PathVariable(value = "id") accountId: UUID): ResponseEntity<List<ListResponse>> {
        val account = accountRepository.findById(accountId)
        if (account.isEmpty) {
            return ResponseEntity.badRequest().build()
        }

        val result = account.get()
            .lists
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
