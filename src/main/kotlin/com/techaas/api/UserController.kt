package com.techaas.api

import com.techaas.dto.requests.DeleteAccountRequest
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account")
class UserController {
    @PostMapping("/register")
    fun registerAccount(@RequestBody registerAccount: RegisterAccountRequest) {

    }

    @PostMapping("/login")
    fun loginAccount(@RequestBody loginAccount: LoginAccountRequest) {

    }


    @DeleteMapping
    fun deleteUser(@RequestBody deleteUser: DeleteAccountRequest) {

    }

    @PutMapping
    fun updateAccount(@RequestBody updateAccount: RegisterAccountRequest) {

    }

}