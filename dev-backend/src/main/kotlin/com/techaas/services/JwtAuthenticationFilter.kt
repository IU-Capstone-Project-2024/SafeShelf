package com.techaas.services

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import com.fasterxml.jackson.databind.ObjectMapper
import com.techaas.dto.responses.ApiErrorResponse
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.security.SignatureException
import org.springframework.beans.factory.annotation.Qualifier

@Component
class JwtAuthenticationFilter(
    private val userDetailsService: CustomUserDetailsService,
    private val tokenService: TokenService,
    @Qualifier("DisableSecurity")
    private val allowSecurity: AllowSecurity
) : OncePerRequestFilter() {

    private val objectMapper = ObjectMapper()

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        val path = request.requestURI
        if (allowSecurity.permitEndpoint(path)) {
            filterChain.doFilter(request, response)
            return
        }
        if (authHeader.doesNotContainBearerToken()) {
            sendErrorResponse(response, "Token is missing or invalid", request)
            return
        }
        val jwtToken = authHeader!!.extractTokenValue()
        try {
            val login = tokenService.extractLogin(jwtToken)
            if (login != null && SecurityContextHolder.getContext().authentication == null) {
                val foundUser = userDetailsService.loadUserByUsername(login)
                if (tokenService.isValid(jwtToken, foundUser)) {
                    updateContext(foundUser, request)
                }
            }
        } catch (e: ExpiredJwtException) {
            sendErrorResponse(response, "JWT token is expired", request)
            return
        } catch (e: SignatureException) {
            sendErrorResponse(response, "JWT validity cannot be asserted.", request)
            return
        } catch (e: Exception) {
            sendErrorResponse(response, "Failed to parse or validate token", request)
            return
        }
        filterChain.doFilter(request, response)
    }

    private fun String?.doesNotContainBearerToken() =
        this == null || !this.startsWith("Bearer ")

    private fun String.extractTokenValue() =
        this.substringAfter("Bearer ")

    private fun updateContext(foundUser: UserDetails, request: HttpServletRequest) {
        val authToken = UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authToken
    }

    private fun sendErrorResponse(response: HttpServletResponse, message: String, request: HttpServletRequest) {

        val errorResponse = ApiErrorResponse(
            code = HttpServletResponse.SC_UNAUTHORIZED.toString(),
            description = "You are not authorized!",
            exceptionMessage = message,
            exceptionName = "UnauthorizedUserException",
        )
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}