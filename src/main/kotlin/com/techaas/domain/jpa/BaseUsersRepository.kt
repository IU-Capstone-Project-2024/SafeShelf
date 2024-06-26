package com.techaas.domain.jpa

import com.techaas.domain.entity.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseUsersRepository : JpaRepository<UsersEntity, Long> {
    fun findByLogin(name: String): UsersEntity

}