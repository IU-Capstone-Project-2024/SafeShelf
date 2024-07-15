package com.techaas.tools

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.dto.ProductToGenerator
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@Component
class ConvertToGenerator {
    fun convert(products: List<UserProductEntity>): List<ProductToGenerator> {
        return products.map { userProductEntity ->
            val product: ProductEntity = userProductEntity.product
            val overallWeight: BigDecimal = userProductEntity.weight
            val expirationDate: LocalDate = userProductEntity.expirationDate
            ProductToGenerator(
                name = product.name,
                weight = overallWeight,
                kcal = product.kcal,
                proteins = product.proteins,
                fats = product.fats,
                carbohydrates = product.carbohydrates,
                expirationDate = ChronoUnit.DAYS.between(LocalDate.now(), expirationDate)
            )
        }
    }
}