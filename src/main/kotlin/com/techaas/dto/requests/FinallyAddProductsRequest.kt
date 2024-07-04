package com.techaas.dto.requests

import com.techaas.data_entities.ProductWithDate

data class FinallyAddProductsRequest(
    val login : String,
    val products: List<ProductWithDate>
)