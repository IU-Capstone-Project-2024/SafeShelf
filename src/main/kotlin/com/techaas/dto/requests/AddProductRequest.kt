package com.techaas.dto.requests

import com.techaas.dto.responses.ProductResponse

data class AddProductRequest(
    val products: List<ProductResponse>
)