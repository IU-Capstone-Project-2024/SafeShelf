package com.techaas.domain.jpa

import com.techaas.domain.entity.ProductsEntity
import com.techaas.domain.jpa.bases_quieries.BaseProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class JpaProductService(
    @Autowired
    private val baseProductRepository: BaseProductRepository
) {
    fun existProduct(name: String, weight: BigDecimal): Boolean {
        return baseProductRepository.existsProductsEntityByNameAndWeight(name, weight)
    }

    fun saveProduct(name: String, weight: BigDecimal) {
        val productEntity = ProductsEntity(
            name= name,
            weight = weight
        )
        baseProductRepository.save(productEntity)
    }

    fun getProductByName(name: String) : ProductsEntity {
        return baseProductRepository.getProductsEntityByName(name)
    }


}
