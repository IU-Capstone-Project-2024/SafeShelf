package com.techaas.endpoints

import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.requests.UpdateUserRequest
import com.techaas.dto.responses.LoginAccountResponse
import com.techaas.dto.responses.UserDataResponse
import com.techaas.services.AccountService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/account")
class UserController(
    private val accountService: AccountService,
) {
    @PostMapping("/register")
    fun register(@RequestBody registerAccount: RegisterAccountRequest) =
        accountService.registration(registerAccount)


    @PostMapping("/login")
    fun login(@RequestBody loginAccount: LoginAccountRequest): LoginAccountResponse =
        accountService.login(loginAccount)

    @PutMapping
    fun update(@RequestBody updateAccount: UpdateUserRequest): UserDataResponse =
        accountService.updateInfo(updateAccount)


    @GetMapping("/{login}")
    fun getUserInfo(@PathVariable login: String): UserDataResponse =
        accountService.getInfo(login)
}