package com.techaas.services

import com.techaas.domain.entity.ProductEntity
import com.techaas.dto.requests.AddProductRequest
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

@Component
@RequiredArgsConstructor
class ProductService {
    @Transactional
    fun saveProducts(addProductEntity: AddProductRequest) {
        // call qr's processor

    }
}