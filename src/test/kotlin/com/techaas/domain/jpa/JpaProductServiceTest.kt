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

    val name = "TestProduct"
    val weight = BigDecimal("2.5")
    val carbohydrates = BigDecimal("3.333")
    val kcal = BigDecimal("1000")
    val fats = BigDecimal("3.3")
    val proteins = BigDecimal("4")

    @Test
    fun testExistProduct() {
        jpaProductService.saveProduct(name, weight, carbohydrates, kcal, fats, proteins)

        assertTrue(jpaProductService.existProduct(name, weight))
    }

    @Test
    fun testSaveAndGetProduct() {
        jpaProductService.saveProduct(name, weight, carbohydrates, kcal, fats, proteins)

        val product = jpaProductService.getProductByNameAndWeight(name, weight)

        assertNotNull(product)
        assertEquals(name, product.name)
        assertEquals(weight, product.weight)
        assertEquals(carbohydrates, product.carbohydrates)
        assertEquals(kcal, product.kcal)
        assertEquals(fats, product.fats)
        assertEquals(proteins, product.proteins)
    }

    @Test
    fun testFindAll() {
        jpaProductService.saveProduct(name, weight, carbohydrates, kcal, fats, proteins)
        jpaProductService.saveProduct("milk", weight, carbohydrates, kcal, fats, proteins)

        val products = jpaProductService.findAll()

        assertEquals(products.size, 2)

    }
}
