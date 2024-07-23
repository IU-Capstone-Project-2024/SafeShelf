package com.techaas.domain.jpa.bases_quieries

import com.techaas.data_entities.DishType
import com.techaas.domain.entity.DishesEntity
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseDishesRepository : MongoRepository<DishesEntity, Long> {
    fun save(dishesEntity: DishesEntity): DishesEntity
    fun findDishesEntityByUserId(userId: Long): List<DishesEntity>
    fun deleteDishesEntityById(dishesEntityId: String)
    fun findDishesEntityById(id: String): DishesEntity
    fun deleteDishesEntityByUserIdAndType(id: Long, type: DishType)
}
