package com.techaas.domain.entity

import jakarta.persistence.*
import lombok.Getter
import java.math.BigDecimal
import java.sql.Timestamp

@Entity
@Table(name = "user_product")
@Getter
data class UserProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: UserEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    val product: ProductEntity?,

    @Column(name = "expiration_date")
    var expirationDate: Timestamp,

    @Column(name = "weight")
    var weight: BigDecimal?
)