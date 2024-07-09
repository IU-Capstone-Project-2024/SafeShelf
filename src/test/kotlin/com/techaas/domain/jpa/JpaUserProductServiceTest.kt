package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.IntegrationTest
import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.tools.ConvertToProductWithDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDate

@Transactional
@SpringBootTest
class JpaUserProductServiceTest : IntegrationTest() {

    @Autowired
    private lateinit var jpaUserProductService: JpaUserProductService

    @Autowired
    private lateinit var jpaProductService: JpaProductService

    @Autowired
    private lateinit var jpaUserService: JpaUserService

    @Autowired
    private lateinit var converter: ConvertToProductWithDate

    val login = "testuser"
    val password = "password123"
    val name = "John"
    val surname = "Doe"
    val age = 30
    val sex = Sex.M

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
        jpaUserService.saveUser(login, password, name, surname, age, sex)
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
    fun testSaveProduct() {
        preparation()

        val savedProducts = jpaUserProductService.getProductsByUser(account)
        assertEquals(savedProducts.size, 3)
        assertNotNull(savedProducts)
    }

    @Test
    fun testGetProductsByAccount() {
        preparation()

        val products: List<UserProductEntity> = jpaUserProductService.getProductsByUser(user = account)

        assertEquals(products.size, 3)
    }

    @Test
    fun testUpdateProductWeight() {
        preparation()

        val updatedProduct: UserProductEntity =
            jpaUserProductService.getProductByAccountAndProduct(account, product1)

        jpaUserProductService.updateProductWeight(updatedProduct.id, BigDecimal("0"))

        assertEquals(updatedProduct.weight, BigDecimal("0"))
    }

    @Test
    fun testDeleteProduct() {
        preparation()

        jpaUserProductService.deleteProduct(account, product1)

        val products = jpaUserProductService.getProductsByUser(account)
        assertEquals(products.size, 2)
    }

    @Test
    fun testUpdateProductDate() {
        preparation()
        val product = jpaUserProductService.getProductByAccountAndProduct(account, product1)

        jpaUserProductService.updateProductDate(product.id, LocalDate.MIN)

        assertEquals(product.expirationDate, LocalDate.MIN)
    }

    @Test
    fun testUpdateWeightBySameDate() {
        preparation()

        jpaUserProductService.saveProduct(account, converter.convert(product1, expirationDate))

        val productEntity: UserProductEntity = jpaUserProductService.getProductByAccountAndProduct(account, product1)
        assertEquals(productEntity.weight, BigDecimal("5.0"))
    }
}
