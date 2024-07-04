package com.techaas.domain.jpa

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import java.math.BigDecimal

@Component
@RequiredArgsConstructor
class JpaProductService(
    private val baseProductRepository: BaseProductRepository
) {
    fun existProduct(name: String, weight: BigDecimal): Boolean {
        return baseProductRepository.existsProductsEntityByNameAndWeight(name, weight)
    }

    fun saveProduct(
        name: String, weight: BigDecimal, carbohydrates: BigDecimal,
        kcal: BigDecimal,
        fats: BigDecimal,
        proteins: BigDecimal
    ) {
        val productEntity = ProductEntity(
            name = name,
            weight = weight,
            carbohydrates = carbohydrates,
            fats = fats,
            kcal = kcal,
            proteins = proteins
        )
        baseProductRepository.save(productEntity)
    }

    fun getProductByNameAndWeight(name: String, weight: BigDecimal): ProductEntity? {
        return baseProductRepository.getProductsEntityByNameAndWeight(name, weight)
    }

    fun findAll(): List<ProductEntity> {
        return baseProductRepository.findAll()
    }
}
