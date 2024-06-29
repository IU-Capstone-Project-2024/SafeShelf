package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.domain.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BaseUserProductRepository : JpaRepository<UserProductEntity, Long> {
    fun getUserProductEntitiesById(userId: Long): List<UserProductEntity>
    fun deleteUserProductEntityByUserAndProduct(user: UserEntity, product: ProductEntity)
    fun findByUserAndProduct(user: UserEntity, product: ProductEntity): List<UserProductEntity>
}