package com.techaas.domain

import liquibase.Liquibase
import liquibase.database.Database
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.exception.LiquibaseException
import liquibase.resource.FileSystemResourceAccessor
import org.junit.jupiter.api.BeforeAll
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.io.File
import java.io.FileNotFoundException
import java.nio.file.Path
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

@Testcontainers
@SpringJUnitConfig
abstract class IntegrationTest {
    companion object {
        @Container
        val POSTGRES: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:16")
                .withDatabaseName("safeshelf")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true)
                .waitingFor(Wait.forListeningPort())
                .withStartupTimeout(java.time.Duration.ofMinutes(1))


        @BeforeAll
        @JvmStatic
        fun setUp() {
            POSTGRES.start()
            println("PostgreSQL container started with URL: ${POSTGRES.jdbcUrl}")
            verifyDatabaseConnection()
            runMigrations(POSTGRES)
        }

        @JvmStatic
        fun runMigrations(container: JdbcDatabaseContainer<*>) {
            val url: String = container.jdbcUrl
            val user: String = container.username
            val password: String = container.password
            try {
                val connection: Connection = DriverManager.getConnection(
                    url,
                    user,
                    password
                )
                val database: Database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(JdbcConnection(connection))

                val path: Path = File(".")
                    .toPath()
                    .toAbsolutePath()
                    .parent
                    .resolve("migrations")

                val liquibase = Liquibase(
                    "master.xml",
                    FileSystemResourceAccessor(path.toFile()),
                    database
                )
                liquibase.update("")
            } catch (e: SQLException) {
                e.printStackTrace();
            } catch (e: LiquibaseException) {
                throw RuntimeException(e);
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
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
