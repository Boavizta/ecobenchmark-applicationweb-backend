package com.ecobenchmark.kotlinspringjpa.controllers

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class Healthcheck {
    @GetMapping("/healthcheck")
    fun healthcheck(): ResponseEntity<String> {
        return ResponseEntity.noContent().build()
    }
}
