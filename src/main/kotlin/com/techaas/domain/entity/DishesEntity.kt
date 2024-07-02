package com.techaas.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "dishes")
data class DishesEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id : Long = 0,

    @Column(name = "name")
    var name : String,

    @ManyToMany
    @JoinTable(
        name = "ingridients",
        joinColumns = [JoinColumn(name = "dishes_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "user_product_id", referencedColumnName = "id")]
    )
    var ingridients : MutableList<UserProductEntity> = mutableListOf(),

    @Column(name = "description")
    var description : String,

    @Column(name = "type")
    var type : Char,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: UserEntity?
)