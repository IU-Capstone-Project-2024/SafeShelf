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
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", referencedColumnName = "id", nullable = false)
    var user: UserEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", referencedColumnName = "id", nullable = false)
    var product: ProductEntity?,

    @Column(name = "expiration_date")
    var expirationDate: Timestamp,

    @Column(name = "weight")
    var weight: BigDecimal?
)