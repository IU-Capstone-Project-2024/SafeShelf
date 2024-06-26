package com.techaas.domain.entity

import jakarta.persistence.*
import lombok.Getter

@Entity
@Table(name = "user_product")
@Getter
data class UserProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    var userID: UsersEntity?,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productid", nullable = false)
    val productID: ProductsEntity?,

    @Column(name = "expiration_date")
    val expiration_date: String?,

    ) {
    constructor() : this(null, null, null, null)
}
