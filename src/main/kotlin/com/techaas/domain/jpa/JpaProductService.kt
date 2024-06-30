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

    fun saveProduct(name: String, weight: BigDecimal) {
        val productEntity = ProductEntity(
            name = name,
            weight = weight
        )
        baseProductRepository.save(productEntity)
    }

    fun getProductByName(name: String): ProductEntity {
        return baseProductRepository.getProductsEntityByName(name)
    }

    fun findAll(): List<ProductEntity> {
        return baseProductRepository.findAll()
    }


}
