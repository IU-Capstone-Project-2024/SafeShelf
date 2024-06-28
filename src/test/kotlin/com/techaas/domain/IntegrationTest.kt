package com.techaas.domain

import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.sql.DriverManager

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract class IntegrationTest {

    companion object {

        @Container
        val POSTGRES: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:16")
                .withDatabaseName("products")
                .withUsername("postgres")
                .withPassword("postgres")

        @BeforeAll
        @JvmStatic
        fun setUp() {
            POSTGRES.start()
            println("PostgreSQL container started with URL: ${POSTGRES.jdbcUrl}")
            Thread.sleep(5000) // Adjust the sleep duration as needed
            verifyDatabaseConnection()
        }

        @DynamicPropertySource
        @JvmStatic
        fun jdbcProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { POSTGRES.jdbcUrl }
            registry.add("spring.datasource.username") { POSTGRES.username }
            registry.add("spring.datasource.password") { POSTGRES.password }
        }

        private fun verifyDatabaseConnection() {
            val url = POSTGRES.jdbcUrl
            val username = POSTGRES.username
            val password = POSTGRES.password

            try {
                DriverManager.getConnection(url, username, password).use { conn ->
                    if (conn.isValid(2)) {
                        println("Successfully connected to the database at URL: $url")
                    } else {
                        throw RuntimeException("Failed to connect to the database.")
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                throw RuntimeException("Database connection verification failed.", e)
            }
        }
    }
}
