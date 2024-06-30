package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.bases_quieries.BaseUserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class JpaUserService(
    private val baseUserRepository: BaseUserRepository
) {
    fun checkIfTheUserExists(login: String): Boolean {
        return baseUserRepository.existsUsersEntityByLogin(login)
    }

    fun saveUser(login: String, password: String, name: String, surname: String, age: Int, sex: Sex) {
        val userEntity = UserEntity(
            login = login,
            password = password,
            name = name,
            surname = surname,
            age = age,
            sex = sex
        )
        baseUserRepository.save(userEntity)
    }

    fun updateUser(login: String, password: String, name: String, surname: String, age: Int, sex: Sex) {
        val usersEntity= baseUserRepository.findByLogin(login)
        usersEntity.login = login
        usersEntity.password = password
        usersEntity.name = name
        usersEntity.surname = surname
        usersEntity.age = age
        usersEntity.sex = sex
        baseUserRepository.save(usersEntity)
    }

    fun checkAuthorizationAccess(login: String, password: String): Boolean {
        return baseUserRepository.existsUsersEntityByLoginAndPassword(login, password)
    }

    fun getUser(login: String): UserEntity {
        return baseUserRepository.findByLogin(login)
    }


    fun findAll(): List<UserEntity> {
        return baseUserRepository.findAll()
    }
}