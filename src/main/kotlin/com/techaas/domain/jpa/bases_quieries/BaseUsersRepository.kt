package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseUsersRepository : JpaRepository<UsersEntity, Long> {
    fun findByLogin(name: String): UsersEntity
    fun existsUsersEntityByLoginAndPassword(login: String, password: String): Boolean
    fun existsUsersEntityByLogin(login: String): Boolean
    fun getUsersEntityById(userId: Long): UsersEntity
}