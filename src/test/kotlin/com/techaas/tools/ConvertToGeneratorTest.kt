package com.techaas.tools

import com.techaas.data_entities.Sex
import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.JpaProductService
import com.techaas.domain.jpa.JpaUserProductService
import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.ProductToGenerator
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@SpringBootTest
@Transactional
class ConvertToGeneratorTest {
    @Autowired
    private lateinit var jpaUserProductService: JpaUserProductService

    @Autowired
    private lateinit var jpaProductService: JpaProductService

    @Autowired
    private lateinit var jpaUserService: JpaUserService

    @Autowired
    private lateinit var converter: ConvertToProductWithDate

    @Autowired
    private lateinit var convertToGenerator: ConvertToGenerator

    val login = "testuser"
    val password = "password123"
    val name = "John"
    val surname = "Doe"
    val height = 160
    val weight = 55
    val age = 30
    val sex = Sex.M
    val lifestyle = "Office worker"
    val goal = "less"


    val nameProduct = "TestProduct"
    val weightProduct = BigDecimal("2.5")
    val carbohydratesProduct = BigDecimal("3.333")
    val kcalProduct = BigDecimal("1000")
    val fatsProduct = BigDecimal("3.3")
    val proteinsProduct = BigDecimal("4")
    val expirationDate: LocalDate = LocalDate.now()

    lateinit var account: UserEntity
    lateinit var product1: ProductEntity
    lateinit var product2: ProductEntity
    lateinit var product3: ProductEntity


    fun preparation() {
        jpaUserService.saveUser(login, password, name, surname, height, weight, age, sex, lifestyle, goal)
        account = jpaUserService.getUser(login)
        jpaProductService.saveProduct(
            nameProduct,
            weightProduct,
            carbohydratesProduct,
            kcalProduct,
            fatsProduct,
            proteinsProduct
        )
        jpaProductService.saveProduct(
            "bread",
            BigDecimal("3.3"),
            carbohydratesProduct,
            kcalProduct,
            fatsProduct,
            proteinsProduct
        )
        jpaProductService.saveProduct(
            "juice",
            BigDecimal("3.2"),
            carbohydratesProduct,
            kcalProduct,
            fatsProduct,
            proteinsProduct
        )
        product1 = jpaProductService.getProductByNameAndWeight(nameProduct, weightProduct)
        product2 = jpaProductService.getProductByNameAndWeight("bread", BigDecimal("3.3"))
        product3 = jpaProductService.getProductByNameAndWeight("juice", BigDecimal("3.2"))
        jpaUserProductService.saveProduct(account, converter.convert(product1, expirationDate))
        jpaUserProductService.saveProduct(account, converter.convert(product2, expirationDate))
        jpaUserProductService.saveProduct(account, converter.convert(product3, expirationDate))
    }


    @Test
    fun convert() {
        preparation()
        val productsToGenerator: List<ProductToGenerator> =
            convertToGenerator.convert(jpaUserProductService.getProductsByUser(account))

        productsToGenerator.forEach { productsToGenerator -> println(productsToGenerator) }

        assertEquals(productsToGenerator.size, 3)
    }
}