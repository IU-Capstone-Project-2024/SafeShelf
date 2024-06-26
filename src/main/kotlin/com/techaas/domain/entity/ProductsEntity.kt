package com.techaas.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "Products")
data class ProductsEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "name")
    var name: String,

    @Column(name = "weight")
    val weight: BigDecimal

) {
    constructor() : this(null, "", BigDecimal.ZERO)
}
