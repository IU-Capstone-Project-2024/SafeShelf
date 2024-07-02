package com.techaas.services

import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.requests.UpdateUserRequest
import com.techaas.dto.responses.UserDataResponse
import com.techaas.exceptions.LoginPasswordMismatchException
import com.techaas.exceptions.UserAlreadyExistsException
import com.techaas.exceptions.UserDoesntExistException
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class AccountService(
    val jpaUserService: JpaUserService
) {
    @Transactional
    fun login(accountRequest: LoginAccountRequest) {
        if (!jpaUserService.checkIfTheUserExists(accountRequest.login)) {
            throw UserDoesntExistException("User is not registered yet")
        } else if (!jpaUserService.checkAuthorizationAccess(accountRequest.login, accountRequest.password)) {
            throw LoginPasswordMismatchException("Incorrect password")
        }
    }

    @Transactional
    fun registration(registerEntity: RegisterAccountRequest) {
        if (jpaUserService.checkIfTheUserExists(registerEntity.login)) {
            throw UserAlreadyExistsException("User already exists")
        }
        jpaUserService.saveUser(
            registerEntity.login,
            registerEntity.password,
            registerEntity.name,
            registerEntity.surname,
            registerEntity.age,
            registerEntity.sex
        )
    }

    @Transactional
    fun updateInfo(updateAccount: UpdateUserRequest) {
        if (!jpaUserService.checkIfTheUserExists(updateAccount.oldLogin)) {
            throw UserDoesntExistException("User is not registered yet")
        }
        jpaUserService.updateUser(
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
        if (!jpaUserService.checkIfTheUserExists(login)) {
            throw UserDoesntExistException("User is not registered yet")
        }
        val user = jpaUserService.getUser(login)
        val userResponse = UserDataResponse(
            login = user.login,
            name = user.name,
            surname = user.surname,
            age = user.age,
            sex = user.sex
        )
        return userResponse
    }
}

