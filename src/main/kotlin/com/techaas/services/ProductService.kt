package com.techaas.services

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.JpaProductService
import com.techaas.domain.jpa.JpaUserProductService
import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.ProductWithDate
import com.techaas.dto.requests.AddProductRequest
import com.techaas.dto.requests.DecodeReceiptRequest
import com.techaas.dto.requests.FinallyAddProductsRequest
import com.techaas.dto.responses.TempProductsResponse
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component

@Component
@RequiredArgsConstructor
class ProductService(
    private val qrAnalyzerService: QrAnalyzerService,
    private val jpaUserService: JpaUserService,
    private val jpaUserProductService: JpaUserProductService
) {
    @Transactional
    fun saveProducts(finallyAddProductsRequest: FinallyAddProductsRequest) {
        val userEntity: UserEntity = jpaUserService.getUser(finallyAddProductsRequest.login)
        for (product: ProductWithDate in finallyAddProductsRequest.products) {
            jpaUserProductService.saveProduct(
                user = userEntity,
                expirationDate = product.date,
                weight = product.weight,
                product = product
            )

        }
    }

    @Transactional
    fun getTempProducts(addProductEntity: AddProductRequest): List<ProductWithDate> {
        val rawReceiptId = addProductEntity.metaStringProducts
        val decodeReceiptRequest = DecodeReceiptRequest(rawReceiptId)
        val parsedProducts = qrAnalyzerService.getReceipt(decodeReceiptRequest)
        return TempProductsResponse(parsedProducts).products
    }
}