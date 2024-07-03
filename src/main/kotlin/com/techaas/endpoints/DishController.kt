package com.techaas.endpoints

import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/dish")
class DishController {
    @PostMapping("/login")
    fun login(@RequestBody registerAccount: RegisterAccountRequest) {
        println("approved!")
    }
}