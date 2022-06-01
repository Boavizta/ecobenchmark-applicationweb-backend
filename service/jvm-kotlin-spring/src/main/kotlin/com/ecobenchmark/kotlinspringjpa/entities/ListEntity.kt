package com.ecobenchmark.kotlinspringjpa.entities

import org.hibernate.annotations.GenericGenerator
import java.time.Instant
import java.util.*
import javax.persistence.*

@Entity(name = "list")
class ListEntity(
    @ManyToOne
    var account: Account,
    var name: String,
    var creationDate: Instant,
    @OneToMany(mappedBy = "list")
    var tasks: List<Task>
) {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    var id: UUID? = null
}
