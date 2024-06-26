package com.techaas.domain

import org.springframework.stereotype.Service

@Service
class JpaProductService(
    //подтягиваем JPA продуктов из конфиг класса
) {
    fun existProduct(name: String, weight: Double): Boolean {
        return true;
    }

    fun saveProduct(name: String, weight: Double) {

    }

    fun getProductByName(name: String) { //entity for Product

    }


}
