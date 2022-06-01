package com.ecobenchmark.kotlinspringjpa.controllers.addaccount

import com.ecobenchmark.kotlinspringjpa.entities.Account
import com.ecobenchmark.kotlinspringjpa.repositories.AccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
class AddAccount {
    @Autowired
    lateinit var repository: AccountRepository

    @PostMapping("/api/accounts")
    fun addAccount(@RequestBody accountRequest: AccountRequest): AccountResponse {
        val account = Account(accountRequest.login, Instant.now(), emptyList())
        repository.save(account)
        return AccountResponse(account.id!!, account.login, account.creationDate)
    }
}
