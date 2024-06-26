package com.techaas.domain

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service
import java.sql.Timestamp

@Service
@RequiredArgsConstructor
class JpaUserProductService(
    //подтягиваем все JPA
) {
    fun saveProduct(accountId: Long, productId: Long, weight: Double, expirationDate: Timestamp) {

    }

    fun getProductsByAccountId(accountId: Long) {

    }

    fun updateProductWeight(accountId: Long, productId: Long, weight: Double) {

    }

    fun deleteProduct(accountId: Long, productId: Long) {

    }

    fun updateProductDate(accountId: Long, productId: Long, expirationDate: Timestamp) {

    }
}