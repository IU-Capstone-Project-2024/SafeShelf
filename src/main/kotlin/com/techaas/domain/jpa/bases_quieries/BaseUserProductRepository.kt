package com.techaas.domain.jpa.bases_quieries


import com.techaas.domain.entity.ProductsEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.domain.entity.UsersEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BaseUserProductRepository: JpaRepository<UserProductEntity, Long> {
    fun getUserProductEntitiesById(userId: Long): List<UserProductEntity>
    fun deleteUserProductEntityByUserIDAndProductID(userID: UsersEntity, productID: ProductsEntity)
    fun findByUserIDAndProductID(userID: UsersEntity, productID: ProductsEntity): List<UserProductEntity>
}