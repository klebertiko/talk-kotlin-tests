package com.accenture.innersource

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertNotEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class MessageTest {
    val message = Message(message = "Hello")

    @Test
    fun instanceTest() {
        assertNotEquals(message = "", actual = message, illegal = Message(message = "Banana"))
    }
}