package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseUserRepository : JpaRepository<UserEntity, Long> {
    fun findByLogin(name: String): UserEntity
    fun existsUsersEntityByLogin(login: String): Boolean
}