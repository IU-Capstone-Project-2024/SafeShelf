package com.techaas.services

import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.requests.UpdateUserRequest
import com.techaas.dto.responses.LoginAccountResponse
import com.techaas.dto.responses.UserDataResponse
import com.techaas.exceptions.RepeatLoginAfterUpdateException
import com.techaas.exceptions.UserAlreadyExistsException
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class AccountService(
    private val jpaUserService: JpaUserService,
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
            registerRequest.height,
            registerRequest.weight,
            registerRequest.age,
            registerRequest.sex,
            registerRequest.lifestyle,
            registerRequest.goal
        )
    }

    @Transactional
    fun updateInfo(updateAccount: UpdateUserRequest): UserDataResponse {
        if (jpaUserService.checkIfTheUserExists(updateAccount.login) && updateAccount.login != updateAccount.oldLogin) {
            throw RepeatLoginAfterUpdateException("User with this login already exist")
        }

        jpaUserService.updateUser(
            updateAccount.oldLogin,
            updateAccount.login,
            updateAccount.password,
            updateAccount.name,
            updateAccount.surname,
            updateAccount.height,
            updateAccount.weight,
            updateAccount.age,
            updateAccount.sex,
            updateAccount.lifestyle,
            updateAccount.goal
        )
        return UserDataResponse(
            updateAccount.login,
            updateAccount.name,
            updateAccount.surname,
            updateAccount.height,
            updateAccount.weight,
            updateAccount.age,
            updateAccount.sex,
            updateAccount.lifestyle,
            updateAccount.goal
        )
    }

    @Transactional
    fun getInfo(login: String): UserDataResponse {
        val user = jpaUserService.getUser(login)
        val userResponse = UserDataResponse(
            login = user.login,
            name = user.name,
            surname = user.surname,
            height = user.height,
            weight = user.weight,
            age = user.age,
            sex = user.sex,
            lifestyle = user.lifestyle,
            goal = user.goal
        )
        return userResponse
    }
}

