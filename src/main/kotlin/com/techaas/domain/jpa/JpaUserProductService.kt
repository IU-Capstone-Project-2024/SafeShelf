package com.techaas.domain.jpa

import com.techaas.domain.entity.UserProductEntity
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUserProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUsersRepository
import jakarta.validation.constraints.Null
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.sql.Timestamp

@Service
@RequiredArgsConstructor
class JpaUserProductService(
    @Autowired
    private val baseUserProductService: BaseUserProductRepository,

    val baseUsersRepository: BaseUsersRepository,
    val baseProductRepository: BaseProductRepository

) {
    fun saveProduct(accountId: Long, productId: Long, weight: BigDecimal, expirationDate: Timestamp) {
        val user = baseUsersRepository.getUsersEntityById(userId = accountId)
        val product = baseProductRepository.getProductsEntityById(id = productId)
        val userProductModel = UserProductEntity (
            userID = user,
            productID = product,
            weight = weight,
            expiration_date = expirationDate
        )
        baseUserProductService.save(userProductModel)
    }

    fun getProductsByAccountId(accountId: Long) : List<UserProductEntity> {
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
        if (userProductEntity != null) {
            userProductEntity.expiration_date = expirationDate
        }
        baseUserProductService.save(userProductEntity)
    }
}