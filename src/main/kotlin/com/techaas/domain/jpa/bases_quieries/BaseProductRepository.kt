package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.ProductEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.math.BigDecimal

@Repository
interface BaseProductRepository: JpaRepository<ProductEntity, Long> {
    fun existsProductsEntityByNameAndWeight(name: String, weight: BigDecimal): Boolean
    fun getProductsEntityByName(name: String) : ProductEntity
    fun getProductsEntityById(id: Long) : ProductEntity
}