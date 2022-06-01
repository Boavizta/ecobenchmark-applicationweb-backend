package com.ecobenchmark.kotlinspringjpa

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringJpaApplication

fun main(args: Array<String>) {
    runApplication<KotlinSpringJpaApplication>(*args)
}
