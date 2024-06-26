import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.containers.PostgreSQLContainer


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
        }

        @DynamicPropertySource
        @JvmStatic
        fun jdbcProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url") { POSTGRES.jdbcUrl }
            registry.add("spring.datasource.username") { POSTGRES.username }
            registry.add("spring.datasource.password") { POSTGRES.password }
            registry.add("spring.datasource.driverClassName") { POSTGRES.driverClassName }
        }
    }
}
