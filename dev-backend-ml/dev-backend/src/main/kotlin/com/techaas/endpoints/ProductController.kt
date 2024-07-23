package com.techaas.endpoints

import com.techaas.dto.ProductWithDate
import com.techaas.dto.requests.AddProductRequest
import com.techaas.dto.requests.DeleteProductRequest
import com.techaas.dto.requests.FinallyAddProductsRequest
import com.techaas.dto.requests.UpdateProductDateRequest
import com.techaas.dto.responses.UserProductsRepsonse
import com.techaas.services.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController(
    private val productService: ProductService
) {
    @PostMapping("/save")
    fun add(@RequestBody request: FinallyAddProductsRequest) =
        productService.saveProducts(request)

    @PostMapping("/get_temp_products")
    fun getTempProducts(@RequestBody request: AddProductRequest): List<ProductWithDate> =
        productService.getTempProducts(request)

    @GetMapping("/{login}")
    fun get(@PathVariable(value = "login") login: String): List<UserProductsRepsonse> =
        productService.getProducts(login)

    @DeleteMapping("/delete")
    fun delete(@RequestBody request: DeleteProductRequest) =
        productService.deleteProduct(request)

    @PatchMapping("update")
    fun updateDate(@RequestBody request: UpdateProductDateRequest) =
        productService.updateProductDate(request)
}