import com.techaas.domain.jpa.JpaUserService
import com.techaas.dto.requests.GetAccountRequest
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.requests.RegisterAccountRequest
import com.techaas.dto.requests.UpdateUserRequest
import com.techaas.dto.responses.UserDataResponse
import com.techaas.exceptions.LoginPasswordUnmatch
import com.techaas.exceptions.UserAlreadyExists
import com.techaas.exceptions.UserDoesntExistException
import jakarta.transaction.Transactional
import lombok.RequiredArgsConstructor
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus

@Service
@RequiredArgsConstructor
class AccountService(
    val jpaUserService: JpaUserService

) {
    @Transactional
    fun userLogin(accountRequest: LoginAccountRequest): ResponseStatus {
        if (!jpaUserService.checkIfTheUserExists(accountRequest.login)) {
            throw UserDoesntExistException("User is not registered yet")
        } else if (!jpaUserService.checkAuthorizationAccess(accountRequest.login, accountRequest.password)) {
            throw LoginPasswordUnmatch("Incorrect password")
        }
        return ResponseStatus(HttpStatus.ACCEPTED)
    }

    @Transactional
    fun userRegistration(registerEntity: RegisterAccountRequest): ResponseStatus {
        if (jpaUserService.checkIfTheUserExists(registerEntity.login)) {
            throw UserAlreadyExists("User already exists")
        }
        jpaUserService.saveUser(
            registerEntity.login,
            registerEntity.password,
            registerEntity.name,
            registerEntity.surname,
            registerEntity.age,
            registerEntity.sex
        )
        return ResponseStatus(HttpStatus.CREATED)
    }

    @Transactional
    fun userUpdate(updateAccount: UpdateUserRequest): ResponseStatus {
        if (!jpaUserService.checkIfTheUserExists(updateAccount.oldLogin)) {
            throw UserDoesntExistException("User is not registered yet")
        }
        jpaUserService.updateUser(
            updateAccount.login,
            updateAccount.password,
            updateAccount.name,
            updateAccount.surname,
            updateAccount.age,
            updateAccount.sex
        )
        return ResponseStatus(HttpStatus.NO_CONTENT)
    }

    @Transactional
    fun getUser(getAccountRequest: GetAccountRequest): ResponseEntity<UserDataResponse> {
        if (!jpaUserService.checkIfTheUserExists(getAccountRequest.login)) {
            throw UserDoesntExistException("User is not registered yet")
        }
        val user = jpaUserService.getUser(getAccountRequest.login)
        val userResponse = UserDataResponse(
            login = user.login,
            name = user.name,
            surname = user.surname,
            age = user.age,
            sex = user.sex
        )
        return ResponseEntity(userResponse, HttpStatus.OK)
    }
}

