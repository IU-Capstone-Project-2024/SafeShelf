package com.techaas.api

import com.techaas.dto.requests.AddProductRequest
import com.techaas.dto.responses.AddProductResponse
import org.springframework.web.bind.annotation.*

@RestController
class Controller {
    @GetMapping("/products/{id}")
    fun getProductById(@PathVariable id: String): String {
        return id
    }

    @GetMapping("/dishes/{id}")
    fun getDishById(@PathVariable id: String): String {
        return id
    }

    @DeleteMapping("/dishes/{id}")
    fun deleteDishById(@PathVariable id: String): String {
        return id
    }

    @DeleteMapping("/products/{id}")
    fun deleteProductById(@PathVariable id: String): String {
        return id
    }

    @PostMapping("/products")
    fun addProduct(
        @RequestHeader("Account-ID") accountId: Long,
        @RequestBody request: AddProductRequest
    ): AddProductResponse {
        return AddProductResponse()
    }

    @PostMapping("/dish")
    fun addDish(
        @RequestHeader("Account-ID") accountId: Long,
        @RequestBody request: AddProductRequest
    ): AddProductResponse {
        return AddProductResponse()
    }

}