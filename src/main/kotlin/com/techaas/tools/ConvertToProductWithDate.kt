package com.techaas.tools

import com.techaas.domain.entity.ProductEntity
import com.techaas.dto.ProductWithDate
import org.springframework.stereotype.Component
import java.sql.Timestamp
import java.time.LocalDate

@Component
class ConvertToProductWithDate {
    fun convert(productEntity: ProductEntity, date: LocalDate): ProductWithDate {
        return ProductWithDate(
            1,
            productEntity.name,
            productEntity.weight,
            productEntity.kcal,
            productEntity.proteins,
            productEntity.fats,
            productEntity.carbohydrates,
            date
        )
    }
}