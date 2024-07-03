package com.techaas.endpoints

import com.techaas.dto.requests.AddProductRequest
import com.techaas.dto.Product
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/product")
class ProductController {
    @PostMapping("/save")
    fun add(@RequestBody request: AddProductRequest): AddProductRequest? {
        return null
    }

    @PatchMapping("/update/{id}")
    fun update(@PathVariable(value = "id") id: Long): Product? {
        return null
    }

    @GetMapping("/{id}")
    fun get(
        @PathVariable(value = "id") id: Long): Product? {
        return null
    }


    @DeleteMapping("/delete/{id}")
    fun delete(
        @RequestHeader("Login") login: String,
        @PathVariable(value = "id") id: Long
    ): Product? {
        return null
    }
}