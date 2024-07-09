package com.techaas.services

import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.requests.UpdateUserRequest
import com.techaas.dto.responses.LoginAccountResponse
import com.techaas.dto.responses.UserDataResponse
import com.techaas.exceptions.LoginPasswordMismatchException
import com.techaas.exceptions.RepeatLoginAfterUpdateException
import com.techaas.exceptions.UserAlreadyExistsException
import com.techaas.exceptions.UserDoesntExistException
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class AccountService(
    val jpaUserService: JpaUserService,
    private val authenticationService: AuthenticationService
) {
    fun login(accountRequest: LoginAccountRequest): LoginAccountResponse {
        return authenticationService.authentication(accountRequest)
    }

    @Transactional
    fun registration(registerRequest: RegisterAccountRequest) {
        if (jpaUserService.checkIfTheUserExists(registerRequest.login)) {
            throw UserAlreadyExistsException("User already exists")
        }
        jpaUserService.saveUser(
            registerRequest.login,
            registerRequest.password,
            registerRequest.name,
            registerRequest.surname,
            registerRequest.age,
            registerRequest.sex
        )
    }

    @Transactional
    fun updateInfo(updateAccount: UpdateUserRequest): UserDataResponse {
        if (!jpaUserService.checkIfTheUserExists(updateAccount.oldLogin)) {
            throw RepeatLoginAfterUpdateException("User with this login already exists, please use new login")
        }
        jpaUserService.updateUser(
            updateAccount.oldLogin,
            updateAccount.login,
            updateAccount.password,
            updateAccount.name,
            updateAccount.surname,
            updateAccount.age,
            updateAccount.sex
        )
        return UserDataResponse(
            updateAccount.login,
            updateAccount.password,
            updateAccount.name,
            updateAccount.surname,
            updateAccount.age,
            updateAccount.sex
        )
    }

    @Transactional
    fun getInfo(login: String): UserDataResponse {
        val user = jpaUserService.getUser(login)
        val userResponse = UserDataResponse(
            login = user.login,
            password = user.password,
            name = user.name,
            surname = user.surname,
            age = user.age,
            sex = user.sex
        )
        return userResponse
    }
}

