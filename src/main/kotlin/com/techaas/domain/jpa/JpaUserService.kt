package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.bases_quieries.BaseUserRepository
import lombok.RequiredArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class JpaUserService(
    private val baseUserRepository: BaseUserRepository,
    private val encoder: PasswordEncoder
) {
    fun checkIfTheUserExists(login: String): Boolean {
        return baseUserRepository.existsUsersEntityByLogin(login)
    }

    fun saveUser(
        login: String,
        password: String,
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        age: Int,
        sex: Sex,
        lifestyle: String,
        goal: String
    ) {
        val userEntity = UserEntity(
            login = login,
            password = encoder.encode(password),
            name = name,
            surname = surname,
            height = height,
            weight = weight,
            age = age,
            sex = sex,
            lifestyle = lifestyle,
            goal = goal
        )
        baseUserRepository.save(userEntity)
    }

    fun updateUser(
        oldLogin: String,
        login: String,
        password: String,
        name: String,
        surname: String,
        height: Int,
        weight: Int,
        age: Int,
        sex: Sex,
        lifestyle: String,
        goal: String
    ) {
        val usersEntity = baseUserRepository.findByLogin(oldLogin)
        usersEntity.login = login
        usersEntity.password = if (password.isEmpty()) usersEntity.password else encoder.encode(password)
        usersEntity.name = name
        usersEntity.surname = surname
        usersEntity.height = height
        usersEntity.weight = weight
        usersEntity.age = age
        usersEntity.sex = sex
        usersEntity.lifestyle = lifestyle
        usersEntity.goal = goal
        baseUserRepository.save(usersEntity)
    }

    fun checkAuthorizationAccess(login: String, password: String): Boolean {
        val user: UserEntity = baseUserRepository.findByLogin(login)
        return encoder.matches(password, user.password)
    }

    fun getUser(login: String): UserEntity {
        return baseUserRepository.findByLogin(login)
    }


    fun findAll(): List<UserEntity> {
        return baseUserRepository.findAll()
    }
}