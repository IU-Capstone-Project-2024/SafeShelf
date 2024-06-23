package com.techaas.domain.entity

import jakarta.persistence.*
import lombok.Getter

@Entity
@Table(name = "users")
@Getter
class Users(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
)
