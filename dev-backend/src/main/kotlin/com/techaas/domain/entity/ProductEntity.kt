package com.techaas.domain.entity

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @Column(name = "name")
    var name: String,

    @Column(name = "weight")
    val weight: BigDecimal,

    @Column(name = "carbohydrates")
    var carbohydrates: BigDecimal,

    @Column(name = "kcal")
    var kcal: BigDecimal,

    @Column(name = "fats")
    var fats: BigDecimal,

    @Column(name = "proteins")
    var proteins: BigDecimal
)
