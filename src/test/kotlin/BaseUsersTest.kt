import com.techaas.domain.jpa.UsersK
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class BaseUsersTest(
    private val usersK: UsersK
) {

    @Test
    fun findByLoginTest() {
        // Example usage of findUserByLogin method
        val user = usersK.findUserByLogin("example_login")
        assertNotNull(user)
        assertEquals("example_login", user.login) // Assuming UsersEntity has a 'login' property
    }
}
