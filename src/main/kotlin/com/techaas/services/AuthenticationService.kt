package com.techaas.services

import com.techaas.configuration.JwtProperties
import com.techaas.dto.requests.LoginAccountRequest
import com.techaas.dto.responses.LoginAccountResponse
import com.techaas.exceptions.LoginPasswordMismatchException
import com.techaas.repository.RefreshTokenRepository
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.util.*

@Service
class AuthenticationService(
    private val authManager: AuthenticationManager,
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
) {
    fun authentication(authenticationRequest: LoginAccountRequest): LoginAccountResponse {
        try {
            authManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.login,
                    authenticationRequest.password
                )
            )
        } catch (e: BadCredentialsException) {
            throw LoginPasswordMismatchException("Bro, you entered bad password. Try again, young catty.")
        }
        val user = userDetailsService.loadUserByUsername(authenticationRequest.login)
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken(user)
        refreshTokenRepository.save(refreshToken, user)
        return LoginAccountResponse(
            accessToken = accessToken,
            refreshToken = refreshToken
        )
    }

    fun refreshAccessToken(refreshToken: String): String? {
        val extractedLogin = tokenService.extractLogin(refreshToken)
        return extractedLogin?.let { login ->
            val currentUserDetails = userDetailsService.loadUserByUsername(login)
            val refreshTokenUserDetails = refreshTokenRepository.findUserDetailsByToken(refreshToken)
            if (!tokenService.isExpired(refreshToken) && refreshTokenUserDetails?.username == currentUserDetails.username)
                createAccessToken(currentUserDetails)
            else
                null
        }
    }

    private fun createAccessToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getAccessTokenExpiration()
    )

    private fun createRefreshToken(user: UserDetails) = tokenService.generate(
        userDetails = user,
        expirationDate = getRefreshTokenExpiration()
    )

    private fun getAccessTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration)

    private fun getRefreshTokenExpiration(): Date =
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
}