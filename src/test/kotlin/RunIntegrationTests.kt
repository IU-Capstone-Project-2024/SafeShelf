import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test


class RunIntegrationTests: IntegrationTest() {
    @Test
    fun testRunIntegrationTests() {
        assertTrue(POSTGRES.isRunning)
    }
}