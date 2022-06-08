package com.ecobenchmark.kotlinspringjpa.repositories

import com.ecobenchmark.kotlinspringjpa.entities.ListEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.PagingAndSortingRepository
import java.util.UUID

interface ListRepository : PagingAndSortingRepository<ListEntity, UUID>  {

    fun findAllByAccountId(accountId:UUID, pageable: Pageable): List<ListEntity>

}