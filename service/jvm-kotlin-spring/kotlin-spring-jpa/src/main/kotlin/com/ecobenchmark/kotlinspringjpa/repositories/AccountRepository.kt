package com.ecobenchmark.kotlinspringjpa.repositories

import com.ecobenchmark.kotlinspringjpa.entities.Account
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface AccountRepository: CrudRepository<Account, UUID> {
}
