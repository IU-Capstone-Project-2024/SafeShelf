package com.techaas.handler

import com.techaas.dto.responses.ApiErrorResponse
import com.techaas.exceptions.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.sql.SQLException

@RestControllerAdvice
class UserErrorHandler {
    @ExceptionHandler(IncorrectParameterException::class, SQLException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleIncorrectParameters(exception: IncorrectParameterException): ApiErrorResponse {
        return ApiErrorResponse(
            code = HttpStatus.BAD_REQUEST.toString(),
            description = "Некорректно указаны параметры",
            exceptionName = exception.getName(),
            exceptionMessage = exception.message
        )
    }

    @ExceptionHandler(LoginPasswordMismatchException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleLoginPasswordMismatch(exception: LoginPasswordMismatchException): ApiErrorResponse {
        return ApiErrorResponse(
            code = HttpStatus.NOT_FOUND.toString(),
            description = "Неправильно указан пароль",
            exceptionName = exception.getName(),
            exceptionMessage = exception.message
        )
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleRepeatRegistration(exception: UserAlreadyExistsException): ApiErrorResponse {
        return ApiErrorResponse(
            code = HttpStatus.FORBIDDEN.toString(),
            description = "Пользователь уже зарегистрирован",
            exceptionName = exception.getName(),
            exceptionMessage = exception.message
        )
    }

    @ExceptionHandler(UserDoesntExistException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleUserDoesntExist(exception: UserDoesntExistException): ApiErrorResponse {
        return ApiErrorResponse(
            code = HttpStatus.BAD_REQUEST.toString(),
            description = "Пользователь еще не зарегистрирован",
            exceptionName = exception.getName(),
            exceptionMessage = exception.message
        )
    }

    @ExceptionHandler(RepeatLoginAfterUpdateException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleRepeatUsingLogin(exception: RepeatLoginAfterUpdateException): ApiErrorResponse {
        return ApiErrorResponse(
            code = HttpStatus.CONFLICT.toString(),
            description = "Пользователь с таким логином уже существует, пожалуйста, придумайте новый логин",
            exceptionName = exception.getName(),
            exceptionMessage = exception.message
        )
    }
}