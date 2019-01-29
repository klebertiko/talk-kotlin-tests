package com.accenture.innersource

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertEquals

object CalculatorServiceSpecification: Spek({
    describe(description = "CalculatorService") {
        val calculator by memoized { CalculatorService() }

        it(description = "1 + 2 = 3") {
            assertEquals(expected = 3, actual = calculator.sum(1, 2))
        }
    }
})