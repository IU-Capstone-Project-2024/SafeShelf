package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface BaseProductRepository: JpaRepository<ProductEntity, Long> {
    fun existsProductsEntityByNameAndWeight(name: String, weight: BigDecimal): Boolean
    fun getProductEntityByNameAndWeight(name: String, weight: BigDecimal) : ProductEntity
    fun getProductEntityById(id: Long) : ProductEntity
}