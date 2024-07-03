package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.IntegrationTest
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

    val login = "testuser"
    val password = "password123"
    val name = "John"
    val surname = "Doe"
    val age = 30
    val sex = Sex.M

    @Test
    fun checkIfTheUserExists() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)

        assertTrue(jpaUserService.checkIfTheUserExists(login))
        assertFalse(jpaUserService.checkIfTheUserExists("nonexistentuser"))
    }

    @Test
    fun saveUser() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)

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
        jpaUserService.saveUser(login, password, name, surname, age, sex)

        jpaUserService.updateUser(login, "newuser", password, "Jane", "Smith", 25, Sex.F)

        val updatedUser = jpaUserService.getUser("newuser")
        val oldUser = jpaUserService.checkIfTheUserExists(login)
        assertFalse(oldUser)
        assertNotNull(updatedUser)
        assertEquals("newuser", updatedUser.login)
        assertEquals(password, updatedUser.password)
        assertEquals("Jane", updatedUser.name)
        assertEquals("Smith", updatedUser.surname)
        assertEquals(25, updatedUser.age)
        assertEquals(Sex.F, updatedUser.sex)
    }

    @Test
    fun checkAuthorizationAccess() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)

        assertTrue(jpaUserService.checkAuthorizationAccess(login, password))
        assertFalse(jpaUserService.checkAuthorizationAccess(login, "wrongpassword"))
    }

    @Test
    fun getUser() {
        jpaUserService.saveUser(login, password, name, surname, age, sex)

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
