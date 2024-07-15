package com.techaas.configuration

import com.techaas.lifestyle.Lifestyle
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DishConfig {
    @Bean
    fun activityMap(lifestyles: List<Lifestyle>): Map<String, Double> {
        return lifestyles.associateBy({ it.getName() }, { it.getCoefficient() })
    }
}