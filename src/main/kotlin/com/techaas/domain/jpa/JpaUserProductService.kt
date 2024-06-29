package com.techaas.domain.jpa

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUserProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUsersRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.sql.Timestamp

@Component
@RequiredArgsConstructor
class JpaUserProductService(
    private val baseUserProductService: BaseUserProductRepository,
    private val baseUsersRepository: BaseUsersRepository,
    private val baseProductRepository: BaseProductRepository

) {
    fun saveProduct(user: UserEntity, product: ProductEntity, weight: BigDecimal, expirationDate: Timestamp) {
        val userProductModel = UserProductEntity(
            user = user,
            product = product,
            weight = weight,
            expirationDate = expirationDate
        )
        baseUserProductService.save(userProductModel)
    }

    fun getProductsByAccountId(accountId: Long): List<UserProductEntity> {
        return baseUserProductService.getUserProductEntitiesById(userId = accountId)
    }


    fun updateProductWeight(userProductID: Long, weight: BigDecimal) {
        val userProductEntity = baseUserProductService.findById(userProductID).orElse(null)
        if (userProductEntity != null) {
            userProductEntity.weight = weight
        }
        baseUserProductService.save(userProductEntity)
    }

    fun deleteProduct(accountId: Long, productId: Long) {
        val user = baseUsersRepository.getUsersEntityById(userId = accountId)
        val product = baseProductRepository.getProductsEntityById(id = productId)
        baseUserProductService.deleteUserProductEntityByUserIDAndProductID(user, product)
    }

    fun updateProductDate(userProductID: Long, expirationDate: Timestamp) {
        val userProductEntity = baseUserProductService.findById(userProductID).orElse(null)
        userProductEntity.expirationDate = expirationDate
        baseUserProductService.save(userProductEntity)
    }
}