package com.techaas

import jakarta.transaction.Transactional
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@Transactional
class SafeShelfApplicationTest {

    @Test
    fun upContext() {
        assertTrue(true)
    }
}