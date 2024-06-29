package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.IntegrationTest
import com.techaas.domain.entity.UserProductEntity
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.sql.Timestamp

@SpringBootTest
@Transactional
class JpaUserProductServiceTest : IntegrationTest() {

    @Autowired
    private lateinit var jpaUserProductService: JpaUserProductService

    @Autowired
    private lateinit var jpaProductService: JpaProductService

    @Autowired
    private lateinit var jpaUserService: JpaUserService

    val expirationDate: Timestamp = Timestamp.valueOf("2024-12-31 23:59:59")

    val login = "testuser"
    val password = "password123"
    val name = "John"
    val surname = "Doe"
    val age = 30
    val sex = Sex.M

    val nameProduct = "milk"
    val weight = BigDecimal("2.5")

    @Test
    fun testSaveProduct() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)
//        jpaProductService.saveProduct(nameProduct, weight)
//        val account = jpaUserService.getUser(login)
//        val product = jpaProductService.getProductByName(nameProduct)
//        jpaUserProductService.saveProduct(account, product, weight, expirationDate)
//
//        val savedProducts = jpaUserProductService.getProductsByAccountId(account.id)
//        assertNotNull(savedProducts)
    }

    @Test
    fun testGetProductsByAccountId() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)
        val account = jpaUserService.getUser(login)
        jpaProductService.saveProduct(nameProduct, weight)
        jpaProductService.saveProduct("bread", BigDecimal(3.3))
        jpaProductService.saveProduct("juice", BigDecimal(3.2))
        val product1 = jpaProductService.getProductByName(nameProduct)
        val product2 = jpaProductService.getProductByName("bread")
        val product3 = jpaProductService.getProductByName("juice")
        jpaUserProductService.saveProduct(
            account,
            product1,
            BigDecimal("2.3"),
            Timestamp.valueOf("2024-12-31 23:59:59")
        )
        jpaUserProductService.saveProduct(
            account,
            product2,
            BigDecimal("2.5"),
            Timestamp.valueOf("2024-11-31 23:00:00")
        )
        jpaUserProductService.saveProduct(
            account,
            product3,
            BigDecimal("2.2"),
            Timestamp.valueOf("2024-12-31 23:59:59")
        )

//        val products: List<UserProductEntity> = jpaUserProductService.getProductsByAccountId(account.id)
//
//        assertEquals(products.size, 3)
    }

//    @Test
//    fun testUpdateProductWeight() {
//        val accountId = 1L
//        val productId = 1L
//        val initialWeight = BigDecimal("2.5")
//        val updatedWeight = BigDecimal("3.0")
//
//        // Save the product
//        jpaUserProductService.saveProduct(accountId, productId, initialWeight, Timestamp.valueOf("2024-12-31 23:59:59"))
//
//        // Retrieve and update weight
//        val savedProducts = jpaUserProductService.getProductsByAccountId(accountId)
//        val userProductId = savedProducts.first().id
//        if (userProductId != null) {
//            jpaUserProductService.updateProductWeight(userProductId, updatedWeight)
//        }
//
//        // Verify update
//        val updatedProduct = userProductId?.let { baseUserProductRepository.findById(it).orElse(null) }
//        assertNotNull(updatedProduct)
//        if (updatedProduct != null) {
//            assertEquals(updatedWeight, updatedProduct.weight)
//        }
//    }
//
//    @Test
//    fun testDeleteProduct() {
//        val accountId = 1L
//        val productId = 1L
//        val weight = BigDecimal("2.5")
//        val expirationDate = Timestamp.valueOf("2024-12-31 23:59:59")
//
//        // Save the product
//        jpaUserProductService.saveProduct(accountId, productId, weight, expirationDate)
//
//        // Delete the product
//        jpaUserProductService.deleteProduct(accountId, productId)
//
//        // Verify deletion
//        val deletedProduct = baseUserProductRepository.findByUserIDAndProductID(
//            baseUsersRepository.getUsersEntityById(accountId),
//            baseProductRepository.getProductsEntityById(productId)
//        )
//        assertNull(deletedProduct)
//    }
//
//    @Test
//    fun testUpdateProductDate() {
//        val accountId = 1L
//        val productId = 1L
//        val initialExpirationDate = Timestamp.valueOf("2024-12-31 23:59:59")
//        val updatedExpirationDate = Timestamp.valueOf("2025-12-31 23:59:59")
//
//        // Save the product
//        jpaUserProductService.saveProduct(accountId, productId, BigDecimal("2.5"), initialExpirationDate)
//
//        // Update expiration date
//        val savedProducts = jpaUserProductService.getProductsByAccountId(accountId)
//        val userProductId = savedProducts.first().id
//        if (userProductId != null) {
//            jpaUserProductService.updateProductDate(userProductId, updatedExpirationDate)
//        }
//
//        // Verify update
//        val updatedProduct = userProductId?.let { baseUserProductRepository.findById(it).orElse(null) }
//        assertNotNull(updatedProduct)
//        if (updatedProduct != null) {
//            assertEquals(updatedExpirationDate, updatedProduct.expirationDate)
//        }
//    }
}
