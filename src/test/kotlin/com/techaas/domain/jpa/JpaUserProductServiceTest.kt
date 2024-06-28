package com.techaas.domain.jpa

import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUserProductRepository
import com.techaas.domain.jpa.bases_quieries.BaseUsersRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.sql.Timestamp

@SpringBootTest
@Transactional
class JpaUserProductServiceTest {

    @Autowired
    private lateinit var jpaUserProductService: JpaUserProductService

    @Autowired
    private lateinit var baseUsersRepository: BaseUsersRepository

    @Autowired
    private lateinit var baseProductRepository: BaseProductRepository

    @Autowired
    private lateinit var baseUserProductRepository: BaseUserProductRepository

    @Test
    fun testSaveProduct() {
        val accountId = 1L
        val productId = 1L
        val weight = BigDecimal("2.5")
        val expirationDate = Timestamp.valueOf("2024-12-31 23:59:59")

        // Save the product
        jpaUserProductService.saveProduct(accountId, productId, weight, expirationDate)

        // Retrieve and verify
        val savedProducts = jpaUserProductService.getProductsByAccountId(accountId)
        assertEquals(1, savedProducts.size)

        val savedProduct = savedProducts.first()
        savedProduct.userID?.let { assertEquals(accountId, it.id) }
        savedProduct.productID?.let { assertEquals(productId, it.id) }
        assertEquals(weight, savedProduct.weight)
        assertEquals(expirationDate, savedProduct.expiration_date)
    }

    @Test
    fun testUpdateProductWeight() {
        val accountId = 1L
        val productId = 1L
        val initialWeight = BigDecimal("2.5")
        val updatedWeight = BigDecimal("3.0")

        // Save the product
        jpaUserProductService.saveProduct(accountId, productId, initialWeight, Timestamp.valueOf("2024-12-31 23:59:59"))

        // Retrieve and update weight
        val savedProducts = jpaUserProductService.getProductsByAccountId(accountId)
        val userProductId = savedProducts.first().id
        if (userProductId != null) {
            jpaUserProductService.updateProductWeight(userProductId, updatedWeight)
        }

        // Verify update
        val updatedProduct = userProductId?.let { baseUserProductRepository.findById(it).orElse(null) }
        assertNotNull(updatedProduct)
        if (updatedProduct != null) {
            assertEquals(updatedWeight, updatedProduct.weight)
        }
    }

    @Test
    fun testDeleteProduct() {
        val accountId = 1L
        val productId = 1L
        val weight = BigDecimal("2.5")
        val expirationDate = Timestamp.valueOf("2024-12-31 23:59:59")

        // Save the product
        jpaUserProductService.saveProduct(accountId, productId, weight, expirationDate)

        // Delete the product
        jpaUserProductService.deleteProduct(accountId, productId)

        // Verify deletion
        val deletedProduct = baseUserProductRepository.findByUserIDAndProductID(
            baseUsersRepository.getUsersEntityById(accountId),
            baseProductRepository.getProductsEntityById(productId)
        )
        assertNull(deletedProduct)
    }

    @Test
    fun testUpdateProductDate() {
        val accountId = 1L
        val productId = 1L
        val initialExpirationDate = Timestamp.valueOf("2024-12-31 23:59:59")
        val updatedExpirationDate = Timestamp.valueOf("2025-12-31 23:59:59")

        // Save the product
        jpaUserProductService.saveProduct(accountId, productId, BigDecimal("2.5"), initialExpirationDate)

        // Update expiration date
        val savedProducts = jpaUserProductService.getProductsByAccountId(accountId)
        val userProductId = savedProducts.first().id
        if (userProductId != null) {
            jpaUserProductService.updateProductDate(userProductId, updatedExpirationDate)
        }

        // Verify update
        val updatedProduct = userProductId?.let { baseUserProductRepository.findById(it).orElse(null) }
        assertNotNull(updatedProduct)
        if (updatedProduct != null) {
            assertEquals(updatedExpirationDate, updatedProduct.expiration_date)
        }
    }
}
