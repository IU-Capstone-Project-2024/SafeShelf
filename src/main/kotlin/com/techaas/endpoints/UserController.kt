package com.techaas.endpoints

import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RefreshTokenRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.responses.TokenResponse
import com.techaas.services.AuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException


@RestController
@RequestMapping("/account")
class UserController(
    private val authenticationService: AuthenticationService
) {
    @PostMapping("/register")
    fun register(@RequestBody registerAccount: RegisterAccountRequest) {
        println("test123")
    }

    @PostMapping("/login")
    fun login(@RequestBody loginAccountRequest: LoginAccountRequest) =
        authenticationService.authentication(loginAccountRequest)

    @PostMapping("/refresh")
    fun refreshAccessToken(
        @RequestBody request: RefreshTokenRequest
    ): TokenResponse =
        authenticationService.refreshAccessToken(request.token)
            ?.mapToTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token.")

    private fun String.mapToTokenResponse(): TokenResponse =
        TokenResponse(
            token = this
        )

    @PostMapping("/test")
    fun testing_something(@RequestBody loginAccount: LoginAccountRequest) {
        println("test456")
    }

    @PutMapping
    fun update(@RequestBody updateAccount: RegisterAccountRequest) {

    }

}