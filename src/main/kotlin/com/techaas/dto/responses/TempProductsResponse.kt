package com.techaas.dto.responses

import com.techaas.data_entities.ProductWithDate

data class TempProductsResponse(
    val products: List<ProductWithDate>
)