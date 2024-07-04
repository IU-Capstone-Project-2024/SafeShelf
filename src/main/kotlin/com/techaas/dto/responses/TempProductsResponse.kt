package com.techaas.dto.responses

import com.techaas.dto.ProductWithDate

data class TempProductsResponse(
    val products: List<ProductWithDate>
)