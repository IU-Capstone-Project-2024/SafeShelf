package com.techaas.endpoints

import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.requests.UpdateUserRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account")
class UserController {
    @PostMapping("/register")
    fun register(@RequestBody registerAccount: RegisterAccountRequest): String {
        return "ok";
    }

    @PostMapping("/login")
    fun login(@RequestBody loginAccount: LoginAccountRequest): String {
        return "ok";
    }

    @PutMapping
    fun update(@RequestBody updateAccount: UpdateUserRequest) {

    }

    @GetMapping
    fun getUser(@RequestBody getAccountRequest: RegisterAccountRequest) {

    }
}