import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.exceptions.UserDoesntExistException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AccountService(
    val jpaUserService: JpaUserService

) {
    @Transactional
    fun userLogin(accountRequest: LoginAccountRequest) {
        if (!jpaUserService.checkIfTheUserExists(accountRequest.login))
        {
            throw UserDoesntExistException("User is not registered yet")
        }
    }
}