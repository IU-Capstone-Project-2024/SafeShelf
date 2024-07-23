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
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.sql.Timestamp
import java.time.LocalDate

@Component
@RequiredArgsConstructor
class JpaUserProductService(
    private val baseUserProductRepository: BaseUserProductRepository,
    private val baseUserRepository: BaseUserRepository,
    private val baseProductRepository: BaseProductRepository
) {

    @Transactional
    fun saveProduct(user: UserEntity, product: ProductWithDate) {
        val productToAdd: ProductEntity =
            baseProductRepository.getProductEntityByNameAndWeight(product.name, product.weight)
        if (baseUserProductRepository.existsByUserAndProduct(user, productToAdd)) {
            val productUpdate = baseUserProductRepository.getUserProductEntityByUserAndProduct(user, productToAdd)
            if (productUpdate.expirationDate == product.date) {
                productUpdate.weight += product.weight
            }
            baseUserProductRepository.save(productUpdate)
        } else {
            val userProductModel = UserProductEntity(
                user = user,
                product = productToAdd,
                weight = productToAdd.weight,
                expirationDate = product.date
            )
            baseUserProductRepository.save(userProductModel)
        }
    }

    @Transactional
    fun getProductsByUser(user: UserEntity): List<UserProductEntity> {
        return baseUserProductRepository.getUserProductEntitiesByUser(user)
    }

    fun getProductByAccountAndProduct(user: UserEntity, product: ProductEntity): UserProductEntity {
        return baseUserProductRepository.findByUserAndProduct(user, product)
    }


    fun updateProductWeight(id: Long, weight: BigDecimal) {
        val userProductEntity = baseUserProductRepository.findById(id).orElse(null)
        if (userProductEntity != null) {
            userProductEntity.weight = weight
            baseUserProductRepository.save(userProductEntity)
        }
    }

    @Transactional
    fun getUserProductEntityById(id: Long): UserProductEntity =
        baseUserProductRepository.getUserProductEntityById(id)

    @Transactional
    fun deleteProduct(user: UserEntity, product: ProductEntity) {
        baseUserProductRepository.deleteUserProductEntityByUserAndProduct(user, product)
    }

    @Transactional
    fun deleteUserProduct(id: Long) = baseUserProductRepository.deleteUserProductEntityById(id)

    @Transactional
    fun updateProductDate(userProductID: Long, expirationDate: LocalDate) {
        val userProductEntity = baseUserProductRepository.findById(userProductID).orElse(null)
        userProductEntity.expirationDate = expirationDate
        baseUserProductRepository.save(userProductEntity)
    }

    @Transactional
    fun findAll(): List<UserProductEntity> {
        return baseUserProductRepository.findAll()
    }

    @Transactional
    fun checkIfTheProductExistsForTheUser(user: UserEntity, product: ProductEntity): Boolean {
        return baseUserProductRepository.existsByUserAndProduct(user, product)
    }

    @Transactional
    fun getUserProductByID(id: Long) : UserProductEntity {
        return baseUserProductRepository.getUserProductEntityById(id)
    }

    @Transactional
    fun deleteUserProductEntityByID(id: Long) {
        baseUserProductRepository.deleteUserProductEntityById(id)
    }
}