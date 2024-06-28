package com.techaas.domain.jpa

import com.techaas.domain.IntegrationTest
import com.techaas.data_entities.Sex
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class JpaUserServiceTest : IntegrationTest() {

    @Autowired
    private lateinit var jpaUserService: JpaUserService

    @Test
    fun checkIfTheUserExists() {
        val login = "testuser"
        jpaUserService.saveUser(login, "password123", "John", "Doe", 30, Sex.M)
        assertTrue(jpaUserService.checkIfTheUserExists(login))
        assertFalse(jpaUserService.checkIfTheUserExists("nonexistentuser"))
    }

    @Test
    fun saveUser() {
        val login = "testuser"
        jpaUserService.saveUser(login, "password123", "John", "Doe", 30, Sex.M)
        val user = jpaUserService.getUser(login)
        assertNotNull(user)
        assertEquals(login, user.login)
        assertEquals("John", user.name)
        assertEquals("Doe", user.surname)
        assertEquals(30, user.age)
        assertEquals(Sex.M, user.sex)
    }

    @Test
    fun updateUser() {
        val login = "testuser"
        jpaUserService.saveUser(login, "password123", "John", "Doe", 30, Sex.M)
        jpaUserService.updateUser(login, "newpassword", "Jane", "Smith", 25, Sex.F)
        val updatedUser = jpaUserService.getUser(login)
        assertNotNull(updatedUser)
        assertEquals("newpassword", updatedUser.password)
        assertEquals("Jane", updatedUser.name)
        assertEquals("Smith", updatedUser.surname)
        assertEquals(25, updatedUser.age)
        assertEquals(Sex.F, updatedUser.sex)
    }

    @Test
    fun checkAuthorizationAccess() {
        val login = "testuser"
        val password = "password123"
        jpaUserService.saveUser(login, password, "John", "Doe", 30, Sex.M)
        assertTrue(jpaUserService.checkAuthorizationAccess(login, password))
        assertFalse(jpaUserService.checkAuthorizationAccess(login, "wrongpassword"))
    }

    @Test
    fun getUser() {
        val login = "testuser"
        jpaUserService.saveUser(login, "password123", "John", "Doe", 30, Sex.M)
        val user = jpaUserService.getUser(login)
        assertNotNull(user)
        assertEquals(login, user.login)
        assertEquals("John", user.name)
        assertEquals("Doe", user.surname)
    }

    @Test
    fun findAll() {
        val login1 = "testuser1"
        val login2 = "testuser2"
        jpaUserService.saveUser(login1, "password123", "John", "Doe", 30, Sex.M)
        jpaUserService.saveUser(login2, "password456", "Jane", "Smith", 25, Sex.F)
        val users = jpaUserService.findAll()
        assertEquals(2, users.size)
    }
}
