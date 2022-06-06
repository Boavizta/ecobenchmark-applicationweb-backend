package com.ecobenchmark.kotlinspringjpa.repositories

import com.ecobenchmark.kotlinspringjpa.entities.ListEntity
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ListRepository : CrudRepository<ListEntity, UUID> {
}