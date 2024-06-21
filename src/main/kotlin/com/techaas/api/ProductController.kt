package com.techaas.api

import com.techaas.dto.requests.AddProductRequest
import com.techaas.dto.responses.ProductResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController {
    @PostMapping("/save")
    fun addProducts(
        @RequestHeader("Login") login: String,
        @RequestBody request: AddProductRequest
    ): AddProductRequest? {
        return null
    }

    @PatchMapping("/update/{id}")
    fun updateProduct(
        @RequestHeader("Login") login: String,
        @PathVariable(value = "id") id: Long
    ): ProductResponse? {
        return null
    }


    @GetMapping("/list")
    fun getProducts(@RequestHeader("Login") login: String): List<ProductResponse>? {
        return null
    }

    @GetMapping("/{id}")
    fun getProduct(
        @RequestHeader("Login") login: String,
        @PathVariable(value = "id") id: Long
    ): ProductResponse? {
        return null
    }


    @DeleteMapping("/delete/{id}")
    fun deleteProduct(
        @RequestHeader("Login") login: String,
        @PathVariable(value = "id") id: Long
    ): ProductResponse? {
        return null
    }
}