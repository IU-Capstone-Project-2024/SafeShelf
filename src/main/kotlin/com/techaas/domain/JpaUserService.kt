package com.techaas.domain

import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class JpaUserService(
    // подтягиваем методы JPA из конфиг файла
) {
    fun checkIfTheUserExists(login : String) {

    }

    fun saveUser(login : String, password : String, name: String, age: Int, sex : Char) {

    }

    fun updateUser(login : String, password : String, name: String, age: Int, sex : Char) {

    }

    fun checkAuthorizationAccess(login : String, password: String) {
        //bool
    }
    fun getUserInformation(login : String) {

    }
}