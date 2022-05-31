package com.ecobenchmark.kotlinspringjpa.controllers.addlist

import com.ecobenchmark.kotlinspringjpa.entities.ListEntity
import com.ecobenchmark.kotlinspringjpa.repositories.AccountRepository
import com.ecobenchmark.kotlinspringjpa.repositories.ListRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant
import java.util.*

@RestController
class AddList {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var listRepository: ListRepository

    @PostMapping("/api/accounts/{id}/lists")
    fun addList(@PathVariable(value = "id") accountId: UUID, @RequestBody listRequest: ListRequest): ResponseEntity<ListResponse> {
        val account = accountRepository.findById(accountId)
        if (account.isEmpty) {
            return ResponseEntity.badRequest().build()
        }

        val list = ListEntity(account.get(), listRequest.name, Instant.now(), emptyList())
        listRepository.save(list)
        return ResponseEntity.ok().body(ListResponse(list.id!!, list.name, list.creationDate))
    }
}
