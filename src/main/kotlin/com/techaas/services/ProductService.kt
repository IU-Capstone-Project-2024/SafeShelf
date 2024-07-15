package com.techaas.services

import com.techaas.domain.entity.ProductEntity
import com.techaas.domain.entity.UserEntity
import com.techaas.domain.entity.UserProductEntity
import com.techaas.domain.jpa.JpaProductService
import com.techaas.domain.jpa.JpaUserProductService
import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.ProductWithDate
import com.techaas.dto.requests.*
import com.techaas.dto.responses.TempProductsResponse
import com.techaas.dto.responses.UserProductsRepsonse
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class ProductService(
    private val qrAnalyzerService: QrAnalyzerService,
    private val jpaUserService: JpaUserService,
    private val jpaUserProductService: JpaUserProductService,
    private val jpaProductService: JpaProductService
) {
    @Transactional
    fun saveProducts(finallyAddProductsRequest: FinallyAddProductsRequest) {
        val user: UserEntity = jpaUserService.getUser(finallyAddProductsRequest.login)
        for (product: ProductWithDate in finallyAddProductsRequest.products) {
            if (!jpaProductService.existProduct(product.name, product.weight)) {
                jpaProductService.saveProduct(
                    name = product.name,
                    carbohydrates = product.carbohydrates,
                    fats = product.fats,
                    kcal = product.kcal,
                    proteins = product.proteins,
                    weight = product.weight,
                )
            }
            jpaUserProductService.saveProduct(user, product)
        }
    }

    fun getTempProducts(addProductEntity: AddProductRequest): List<ProductWithDate> {
        val rawReceiptId = addProductEntity.metaStringProducts
        val decodeReceiptRequest = DecodeReceiptRequest(rawReceiptId)
        val parsedProducts = qrAnalyzerService.getReceipt(decodeReceiptRequest)
        return TempProductsResponse(parsedProducts).products
    }

    @Transactional
    fun getProducts(login: String): List<UserProductsRepsonse> {
        val userEntity: UserEntity = jpaUserService.getUser(login)
        val userProductEntities: List<UserProductEntity> = jpaUserProductService.getProductsByUser(userEntity)
        val result: MutableList<UserProductsRepsonse> = mutableListOf()
        for (product in userProductEntities) {
            val originalProductEntity: ProductEntity = product.product
            val response = UserProductsRepsonse(
                id = product.id,
                name = originalProductEntity.name,
                weight = product.weight,
                kcal = originalProductEntity.kcal,
                proteins = originalProductEntity.proteins,
                fats = originalProductEntity.fats,
                carbohydrates = originalProductEntity.carbohydrates,
                date = product.expirationDate.toString()
            )
            result.add(response)
        }
        return result.sortedBy { it.id }
    }

    @Transactional
    fun deleteProduct(deleteProductRequest: DeleteProductRequest) =
        jpaUserProductService.deleteUserProduct(deleteProductRequest.userProductID)


    @Transactional
    fun updateProductDate(updateProductDateRequest: UpdateProductDateRequest) {
        val user: UserEntity = jpaUserService.getUser(updateProductDateRequest.login)
        jpaUserProductService.updateProductDate(updateProductDateRequest.productID, updateProductDateRequest.date)

    }
}