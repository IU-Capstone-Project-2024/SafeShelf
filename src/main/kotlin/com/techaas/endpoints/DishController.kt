package com.techaas.endpoints

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/dish")
class DishController(
) {
    @PostMapping("/breakfast/{login}")
    fun generateBreakfast(@PathVariable(name = "login") login: String) {


    }

    @PostMapping("/launch/{login}")
    fun generateLunch(@PathVariable(name = "login") login: String) {

    }

    @PostMapping("/dinner/{login}")
    fun generateDinner(@PathVariable(name = "login") login: String) {

    }
}