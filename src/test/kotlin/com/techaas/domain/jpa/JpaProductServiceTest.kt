package com.techaas.domain.jpa

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@SpringBootTest
@Transactional
class JpaProductServiceTest {

    @Autowired
    private lateinit var jpaProductService: JpaProductService

    @Test
    fun testExistProduct() {
        val productName = "TestProduct"
        val productWeight = BigDecimal("2.5")

        jpaProductService.saveProduct(productName, productWeight)

        assertTrue(jpaProductService.existProduct(productName, productWeight))
    }

    @Test
    fun testSaveAndGetProduct() {
        val productName = "TestProduct"
        val productWeight = BigDecimal("2.5")

        // Save the product
        jpaProductService.saveProduct(productName, productWeight)

        // Retrieve the product
        val product = jpaProductService.getProductByName(productName)

        // Check if product is not null
        assertNotNull(product)
        assertEquals(productName, product.name)
        assertEquals(productWeight, product.weight)
    }
}
