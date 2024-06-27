package com.techaas.dto.requests

import com.techaas.dto.Product

data class AddProductRequest(
    val login: String,
    val products: List<Product>
)