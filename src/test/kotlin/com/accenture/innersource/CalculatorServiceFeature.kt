package com.accenture.innersource

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.gherkin.Feature
import kotlin.test.assertEquals

object CalculatorServiceFeature: Spek({
    Feature(description = "CalculatorService") {
        val calculator by memoized { CalculatorService() }

        lateinit var resut: Any

        Scenario(description = "sum numbers") {
            When(description = "1 + 2") {
                resut = calculator.sum(numberA = 1, numberB = 2)
            }

            Then("it should return 3") {
                assertEquals(expected = 3, actual = resut)
            }
        }
    }
})