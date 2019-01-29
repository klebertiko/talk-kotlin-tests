package com.accenture.innersource

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CalculatorServiceJUnit5Test {

    private val calculator = CalculatorService()

    @Nested
    inner class Sum {
        @Test
        fun `1 + 2 should return 3`() {
            assertEquals(expected = 3, actual = calculator.sum(numberA = 1, numberB = 2))
        }
    }

    @Nested
    inner class Sub {
        @Test
        fun `2 - 1 should return 1`() {
            assertEquals(expected = 1, actual = calculator.sub(numberA = 2, numberB = 1))
        }
    }
}