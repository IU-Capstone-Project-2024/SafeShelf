package com.techaas.configuration

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.MongoTemplate

@Configuration
class MongoDBInit(private val mongoTemplate: MongoTemplate) {

    @Bean
    fun init(): CommandLineRunner {
        return CommandLineRunner {
            if (!mongoTemplate.collectionExists("dishes")) {
                mongoTemplate.createCollection("dishes")
                println("Created 'dishes' collection in MongoDB.")
            } else {
                println("'dishes' collection already exists.")
            }
        }
    }
}