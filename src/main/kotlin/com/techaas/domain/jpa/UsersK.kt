package com.techaas.domain.jpa

import com.techaas.domain.entity.UsersEntity
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.util.function.Function
import java.util.stream.Collectors


@Component
@RequiredArgsConstructor
class UsersK (
    val baseUsersRepository: BaseUsersRepository
) {
    fun findUserByLogin(login: String): UsersEntity {
        return baseUsersRepository.findByLogin(login)
    }
}