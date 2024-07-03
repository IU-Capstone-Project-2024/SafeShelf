package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.DishesEntity
import com.techaas.domain.entity.UserEntity
import jakarta.persistence.Id
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseDishesRepository : JpaRepository<DishesEntity, Long> {
    fun getDishesEntitiesByUser(userEntity: UserEntity)
    fun getDishesEntityById(id: Long)
    fun deleteDishesEntityById(id: Long)
}