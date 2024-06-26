package com.techaas.endpoints

import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account")
class UserController {
    @PostMapping("/register")
    fun register(@RequestBody registerAccount: RegisterAccountRequest) {

    }

    @PostMapping("/login")
    fun login(@RequestBody loginAccount: LoginAccountRequest) {

    }

    @PutMapping
    fun update(@RequestBody updateAccount: RegisterAccountRequest) {

    }

}