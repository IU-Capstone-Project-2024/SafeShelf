package com.techaas.domain.jpa.bases_quieries

import com.techaas.domain.entity.ProductsEntity
import org.springframework.data.jpa.repository.JpaRepository

interface BaseProductRepository: JpaRepository<ProductsEntity, Long> {


}