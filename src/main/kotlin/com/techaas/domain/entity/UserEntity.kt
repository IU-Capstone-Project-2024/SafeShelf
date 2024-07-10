package com.techaas.domain.entity

import com.techaas.data_entities.Sex
import jakarta.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long = 0,

    @Column(name = "login")
    var login: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "name")
    var name: String,

    @Column(name = "surname")
    var surname: String,

    @Column(name = "height")
    var height: Int,

    @Column(name = "weight")
    var weight: Int,

    @Column(name = "age")
    var age: Int,

    @Column(name = "sex")
    @Enumerated(EnumType.STRING)
    var sex: Sex,

    @Column(name = "lifestyle")
    var lifestyle: String,

    @Column(name = "goal")
    var goal: String
)
