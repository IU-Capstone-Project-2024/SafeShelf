package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.bases_quieries.BaseUsersRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class JpaUserService(
    private val baseUsersRepository: BaseUsersRepository
) {
    fun checkIfTheUserExists(login: String): Boolean {
        return baseUsersRepository.existsUsersEntityByLogin(login)
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
        baseUsersRepository.save(userEntity)
    }

    fun updateUser(login: String, password: String, name: String, surname: String, age: Int, sex: Sex) {
        val usersEntity = baseUsersRepository.findByLogin(login) // check that entity does not exist
        usersEntity.login = login
        usersEntity.password = password
        usersEntity.name = name
        usersEntity.surname = surname
        usersEntity.age = age
        usersEntity.sex = sex
        baseUsersRepository.save(usersEntity)
    }

    fun checkAuthorizationAccess(login: String, password: String): Boolean {
        return baseUsersRepository.existsUsersEntityByLoginAndPassword(login, password)
    }

    fun getUser(login: String): UserEntity {
        return baseUsersRepository.findByLogin(login)
    }


    fun findAll(): List<UserEntity> {
        return baseUsersRepository.findAll()
    }
}