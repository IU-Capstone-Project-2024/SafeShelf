package com.techaas.domain.entity

import com.techaas.data_entities.DishType
import com.techaas.dto.IngredientsEntity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "dishes")
data class DishesEntity (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: String? = null,

    var name: String,
    var ingredients: List<IngredientsEntity>,
    var description: String,
    var type: DishType,
    var userId: Long
)
