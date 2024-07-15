package com.techaas.dto.requests

import com.techaas.dto.ProductToGenerator

data class GenerateDishRequest(
    val kpfc: Array<DoubleArray>,
    val productToGenerators: List<ProductToGenerator>

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GenerateDishRequest

        if (!kpfc.contentDeepEquals(other.kpfc)) return false
        if (productToGenerators != other.productToGenerators) return false

        return true
    }

    override fun hashCode(): Int {
        var result = kpfc.contentDeepHashCode()
        result = 31 * result + productToGenerators.hashCode()
        return result
    }
}