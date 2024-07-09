package com.techaas.repository

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Repository
import com.techaas.domain.entity.UserEntity
import com.techaas.data_entities.Sex
import java.util.*

@Repository
class UserRepository(
    private val encoder: PasswordEncoder
) {

    private val users = mutableSetOf(
        UserEntity(
            id = 0,
            login = "Azaki",
            password = encoder.encode("pass1"),
            name = "Andrew",
            surname = "Lex",
            age = 20,
            sex = Sex.M,
            lifestyle = "Gay"
        ),
        UserEntity(
            id = 1,
            login = "Junkyyz",
            password = encoder.encode("pass2"),
            name = "Nikolai",
            surname = "Jonez",
            age = 20,
            sex = Sex.M,
            lifestyle = "Gay"
        ),
        UserEntity(
            id = 2,
            login = "Emapfff",
            password = encoder.encode("pass3"),
            name = "Emil",
            surname = "Asstrahan",
            age = 20,
            sex = Sex.M,
            lifestyle = "Gay"
        ),
    )

    fun save(user: UserEntity): Boolean {
        val updated = user.copy(password = encoder.encode(user.password))

        return users.add(updated)
    }

    fun findByLogin(login: String): UserEntity? =
        users
            .firstOrNull { it.login == login }

    fun findAll(): Set<UserEntity> =
        users

    fun findByID(id: Long): UserEntity? =
        users
            .firstOrNull { it.id == id }

    fun deleteByUUID(id: Long): Boolean {
        val foundUser = findByID(id)

        return foundUser?.let {
            users.removeIf {
                it.id == id
            }
        } ?: false
    }
}