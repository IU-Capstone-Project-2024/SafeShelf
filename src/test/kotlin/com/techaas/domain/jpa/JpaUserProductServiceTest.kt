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
        jpaProductService.saveProduct(nameProduct, weight)
        val account = jpaUserService.getUser(login)
        val product = jpaProductService.getProductByName(nameProduct)
        jpaUserProductService.saveProduct(account, product, weight, expirationDate)

        val savedProducts = jpaUserProductService.getProductsByAccountId(account.id)
        assertNotNull(savedProducts)
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
        jpaUserProductService.saveProduct(account, product1, BigDecimal("2.3"), expirationDate)
        jpaUserProductService.saveProduct(account, product2, BigDecimal("2.5"), expirationDate)
        jpaUserProductService.saveProduct(account, product3, BigDecimal("2.2"), expirationDate)


        val products: List<UserProductEntity> = jpaUserProductService.getProductsByAccountId(account.id)

        assertEquals(products.size, 3)
    }

    @Test
    fun testUpdateProductWeight() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)
        val account = jpaUserService.getUser(login)
        jpaProductService.saveProduct(nameProduct, weight)
        jpaProductService.saveProduct("bread", BigDecimal(3.3))
        jpaProductService.saveProduct("juice", BigDecimal(3.2))
        val product1 = jpaProductService.getProductByName(nameProduct)
        val product2 = jpaProductService.getProductByName("bread")
        val product3 = jpaProductService.getProductByName("juice")
        jpaUserProductService.saveProduct(account, product1, BigDecimal("2.3"), expirationDate)
        jpaUserProductService.saveProduct(account, product2, BigDecimal("2.5"), expirationDate)
        jpaUserProductService.saveProduct(account, product3, BigDecimal("2.2"), expirationDate)

        val updatedProduct: UserProductEntity =
            jpaUserProductService.getProductByAccountAndProduct(account, product1)

        jpaUserProductService.updateProductWeight(updatedProduct.id, BigDecimal("0"))

        assertEquals(updatedProduct.weight, BigDecimal("0"))
    }

    @Test
    fun testDeleteProduct() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)
        val account = jpaUserService.getUser(login)
        jpaProductService.saveProduct(nameProduct, weight)
        jpaProductService.saveProduct("bread", BigDecimal(3.3))
        jpaProductService.saveProduct("juice", BigDecimal(3.2))
        val product1 = jpaProductService.getProductByName(nameProduct)
        val product2 = jpaProductService.getProductByName("bread")
        val product3 = jpaProductService.getProductByName("juice")
        jpaUserProductService.saveProduct(account, product1, BigDecimal("2.3"), expirationDate)
        jpaUserProductService.saveProduct(account, product2, BigDecimal("2.5"), expirationDate)
        jpaUserProductService.saveProduct(account, product3, BigDecimal("2.2"), expirationDate)

        jpaUserProductService.deleteProduct(account.id, product1.id)

        val products = jpaUserProductService.getProductsByAccountId(account.id)
        assertEquals(products.size, 2)
    }

    @Test
    fun testUpdateProductDate() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)
        val account = jpaUserService.getUser(login)
        jpaProductService.saveProduct(nameProduct, weight)
        jpaProductService.saveProduct("bread", BigDecimal(3.3))
        jpaProductService.saveProduct("juice", BigDecimal(3.2))
        val product1 = jpaProductService.getProductByName(nameProduct)
        val product2 = jpaProductService.getProductByName("bread")
        val product3 = jpaProductService.getProductByName("juice")
        jpaUserProductService.saveProduct(account, product1, BigDecimal("2.3"), expirationDate)
        jpaUserProductService.saveProduct(account, product2, BigDecimal("2.5"), expirationDate)
        jpaUserProductService.saveProduct(account, product3, BigDecimal("2.2"), expirationDate)

        val updatingProduct = jpaUserProductService.getProductByAccountIdAndProductId(account.id, product1.id)
        jpaUserProductService.updateProductDate(updatingProduct.id, Timestamp.valueOf("2025-01-01 00:00:00"))
        assertEquals(updatingProduct.expirationDate, Timestamp.valueOf("2025-01-01 00:00:00"))
    }
}
