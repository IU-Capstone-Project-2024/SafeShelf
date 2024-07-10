package com.techaas.domain.jpa

import com.techaas.data_entities.Sex
import com.techaas.domain.IntegrationTest
import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
@Transactional
class JpaUserServiceTest : IntegrationTest() {
    @Autowired
    private lateinit var jpaUserService: JpaUserService


    @Autowired
    private lateinit var encoder: PasswordEncoder

    val login = "testuser"
    val password = "password123"
    val name = "John"
    val surname = "Doe"
    val age = 30
    val height = 160
    val weight = 55
    val sex = Sex.M
    val lifestyle = "Office worker"

    @Test
    fun checkIfTheUserExists() {
        jpaUserService.saveUser(login, password, name, surname, height, weight, age, sex, lifestyle)

        assertTrue(jpaUserService.checkIfTheUserExists(login))
        assertFalse(jpaUserService.checkIfTheUserExists("nonexistentuser"))
    }

    @Test
    fun saveUser() {
        jpaUserService.saveUser(login, password, name, surname, height, weight, age, sex, lifestyle)

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
        jpaUserService.saveUser(login, password, name, surname, height, weight, age, sex, lifestyle)

        jpaUserService.updateUser(login, "newuser", password, "Jane", "Smith",
            111, 1111, 25, Sex.F, lifestyle)

        val updatedUser = jpaUserService.getUser("newuser")
        val oldUser = jpaUserService.checkIfTheUserExists(login)
        assertFalse(oldUser)
        assertNotNull(updatedUser)
        assertEquals("newuser", updatedUser.login)
        assertEquals("Jane", updatedUser.name)
        assertEquals("Smith", updatedUser.surname)
        assertEquals(111, updatedUser.height)
        assertEquals(1111, updatedUser.weight)
        assertEquals(25, updatedUser.age)
        assertEquals(Sex.F, updatedUser.sex)
    }

    @Test
    fun checkAuthorizationAccess() {
        jpaUserService.saveUser(login, password, name, surname, height, weight, age, sex, lifestyle)

        assertTrue(jpaUserService.checkIfTheUserExists(login))
        assertTrue(jpaUserService.checkAuthorizationAccess(login, password))
        assertFalse(jpaUserService.checkAuthorizationAccess(login, "wrongpassword"))
    }

    @Test
    fun getUser() {
        jpaUserService.saveUser(login, password, name, surname, height, weight, age, sex, lifestyle)

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
        jpaUserService.saveUser(login1, "password123", "John", "Doe",height, weight, 30, Sex.M, lifestyle)
        jpaUserService.saveUser(login2, "password456", "Jane", "Smith", height, weight,25, Sex.F, lifestyle)

        val users = jpaUserService.findAll()

        assertEquals(2, users.size)
    }
}
