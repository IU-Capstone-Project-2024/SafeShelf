package com.techaas.endpoints

import com.techaas.dto.Product
import com.techaas.dto.ProductWithDate
import com.techaas.dto.requests.AddProductRequest
import com.techaas.dto.requests.FinallyAddProductsRequest
import com.techaas.services.ProductService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping("/save")
    fun add(@RequestBody request: FinallyAddProductsRequest) {
        productService.saveProducts(request)
    }

    @PostMapping("/get_temp_products")
    fun getTempProducts(@RequestBody request: AddProductRequest): List<ProductWithDate> {
        val result = productService.getTempProducts(request)
        return result
    }

    @GetMapping("/{login}")
    fun get(@PathVariable(value = "login") login: String): List<ProductWithDate>? {
        return null
    }

}