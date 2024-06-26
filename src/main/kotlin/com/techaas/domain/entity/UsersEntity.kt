package com.techaas.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "Users")
data class UsersEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "login")
    var login: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "name")
    val name: String,

    @Column(name = "surname")
    val surname: String,

    @Column(name = "age")
    val age: Int,

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    val sex: Sex

) {
    enum class Sex {
        M, F
    }
}
