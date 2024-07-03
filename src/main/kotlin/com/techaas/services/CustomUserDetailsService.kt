package com.techaas.services

import com.techaas.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.techaas.domain.entity.UserEntity
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByLogin(username)
            ?.mapToUserDetails()
            ?: throw UsernameNotFoundException("User not found!")
    private fun ApplicationUser.mapToUserDetails(): UserDetails =
        User.builder()
            .username(this.login)
            .password(this.password)
            .build()
}