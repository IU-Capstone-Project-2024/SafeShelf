package com.techaas.domain.jpa

import com.techaas.domain.IntegrationTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@SpringBootTest
@Transactional
class JpaProductServiceTest : IntegrationTest() {

    @Autowired
    private lateinit var jpaProductService: JpaProductService

    val productName = "TestProduct"
    val productWeight = BigDecimal("2.5")

    @Test
    fun testExistProduct() {
        jpaProductService.saveProduct(productName, productWeight)

        assertTrue(jpaProductService.existProduct(productName, productWeight))
    }

    @Test
    fun testSaveAndGetProduct() {
        jpaProductService.saveProduct(productName, productWeight)

        val product = jpaProductService.getProductByName(productName)

        assertNotNull(product)
        assertEquals(productName, product.name)
        assertEquals(productWeight, product.weight)
    }

    @Test
    fun testFindAll() {
        jpaProductService.saveProduct(productName, productWeight)
        jpaProductService.saveProduct("milk", BigDecimal(12))

        val products = jpaProductService.findAll()

        assertEquals(products.size, 2)

    }
}
