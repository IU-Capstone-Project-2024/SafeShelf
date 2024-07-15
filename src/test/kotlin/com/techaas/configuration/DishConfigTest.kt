package com.techaas.configuration

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class DishConfigTest {
    @Autowired
    private lateinit var activityMap: Map<String, Double>


    @Test
    fun activityMapTest() {
        assertFalse(activityMap.isEmpty())
        assertEquals(activityMap["Office worker"], 1.2)
    }
}