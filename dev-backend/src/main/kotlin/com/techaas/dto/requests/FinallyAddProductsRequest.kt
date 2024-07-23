package com.techaas.dto.requests

import com.techaas.dto.ProductWithDate

data class FinallyAddProductsRequest(
    val login : String,
    val products: List<ProductWithDate>
)