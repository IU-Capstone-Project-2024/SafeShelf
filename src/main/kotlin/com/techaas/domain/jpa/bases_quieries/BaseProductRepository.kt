package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.ProductsEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.math.BigDecimal

interface BaseProductRepository: JpaRepository<ProductsEntity, Long> {
    fun existsProductsEntityByNameAndWeight(name: String, weight: BigDecimal): Boolean
    fun getProductsEntityByName(name: String) : ProductsEntity
    fun getProductsEntityById(id: Long) : ProductsEntity
}