package com.techaas.domain.jpa

import com.techaas.domain.entity.ProductsEntity
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
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

        // Save the product
        jpaProductService.saveProduct(productName, productWeight)

        // Test existence
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
