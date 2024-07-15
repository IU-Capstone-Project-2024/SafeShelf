package com.techaas.services

import com.techaas.domain.entity.UserEntity
import com.techaas.domain.jpa.JpaUserProductService
import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.ProductToGenerator
import com.techaas.dto.requests.GenerateDishRequest
import com.techaas.tools.ConvertToGenerator
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
@RequiredArgsConstructor
class DishService(
    private val generateKpfcService: GenerateKpfcService,
    private val jpaUserService: JpaUserService,
    private val jpaUserProductService: JpaUserProductService,
    private val convertToGenerator: ConvertToGenerator,
    private val activityMap: Map<String, Double>,
) {
    @Transactional
    fun generateRation(login: String): GenerateDishRequest {
        val user: UserEntity = jpaUserService.getUser(login)
        val productToGenerators: List<ProductToGenerator> = convertToGenerator.convert(jpaUserProductService.getProductsByUser(user))
        val kpfc = generateKpfcService.getKpfc(
            weight = user.weight.toDouble(),
            height = user.height.toDouble(),
            age = user.age,
            sex = user.sex,
            activity = activityMap.getValue(user.lifestyle),
            goal = user.goal
        )
        return GenerateDishRequest(
            kpfc = kpfc,
            productToGenerators = productToGenerators
        )
    }
}