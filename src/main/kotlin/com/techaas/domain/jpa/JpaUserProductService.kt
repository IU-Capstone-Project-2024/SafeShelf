package com.techaas.domain.jpa

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUserProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUserRepository
import com.techaas.dto.ProductWithDate
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

@Component
@RequiredArgsConstructor
class JpaUserProductService(
    private val baseUserProductService: BaseUserProductRepository,
    private val baseUserRepository: BaseUserRepository,
    private val baseProductRepository: BaseProductRepository
) {

    fun saveProduct(user: UserEntity, product: ProductWithDate) {
        val productToAdd: ProductEntity =
            baseProductRepository.getProductEntityByNameAndWeight(product.name, product.weight)
        if (baseUserProductService.existsByUserAndProduct(user, productToAdd)) {
            val productUpdate = baseUserProductService.getUserProductEntityByUserAndProduct(user, productToAdd)
            if (productUpdate.expirationDate == product.date) {
                productUpdate.weight += product.weight
            }
            baseUserProductService.save(productUpdate)
        } else {
            val userProductModel = UserProductEntity(
                user = user,
                product = productToAdd,
                weight = productToAdd.weight,
                expirationDate = product.date
            )
            baseUserProductService.save(userProductModel)
        }
    }

    fun getProductsByUser(user: UserEntity): List<UserProductEntity> {
        return baseUserProductService.getUserProductEntitiesByUser(user)
    }

    fun getProductByAccountAndProduct(user: UserEntity, product: ProductEntity): UserProductEntity {
        return baseUserProductService.findByUserAndProduct(user, product)
    }


    fun updateProductWeight(id: Long, weight: BigDecimal) {
        val userProductEntity = baseUserProductService.findById(id).orElse(null)
        if (userProductEntity != null) {
            userProductEntity.weight = weight
            baseUserProductService.save(userProductEntity)
        }
    }

    fun deleteProduct(user: UserEntity, product: ProductEntity) {
        baseUserProductService.deleteUserProductEntityByUserAndProduct(user, product)
    }

    fun updateProductDate(userProductID: Long, expirationDate: LocalDate) {
        val userProductEntity = baseUserProductService.findById(userProductID).orElse(null)
        userProductEntity.expirationDate = expirationDate
        baseUserProductService.save(userProductEntity)
    }

    fun findAll(): List<UserProductEntity> {
        return baseUserProductService.findAll()
    }
}