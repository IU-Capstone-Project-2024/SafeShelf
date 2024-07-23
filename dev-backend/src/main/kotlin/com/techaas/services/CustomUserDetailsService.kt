package com.techaas.services

import com.techaas.domain.jpa.JpaUserService
import com.techaas.exceptions.UserDoesntExistException
import lombok.RequiredArgsConstructor
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.techaas.domain.entity.UserEntity
@Service
@RequiredArgsConstructor
class CustomUserDetailsService(
    private val jpaUserService: JpaUserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails  {
        if (jpaUserService.checkIfTheUserExists(username)) {
            return jpaUserService.getUser(username).mapToUserDetails()
        } else {
            throw UserDoesntExistException("Sorry bro, you are not registered. Try to register.")
        }
    }

    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.login)
            .password(this.password)
            .build()
}