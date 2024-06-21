package com.techaas.api

import com.techaas.dto.requests.AddUserRequest
import com.techaas.dto.requests.DeleteUserRequest
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class UserController {
    @PostMapping
    fun registrationUser(@RequestBody addUser: AddUserRequest) {

    }

    @DeleteMapping
    fun deleteUser(@RequestBody deleteUser: DeleteUserRequest) {

    }

    @PutMapping
    fun updateUser(@RequestBody updateUser: AddUserRequest) {

    }

}