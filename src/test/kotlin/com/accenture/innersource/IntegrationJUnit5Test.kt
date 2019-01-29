package com.accenture.innersource

import com.github.kittinunf.fuel.Fuel
import io.javalin.Javalin
import junit.framework.TestCase
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationJUnit5Test {

    private lateinit var app: Javalin

    @Test
    fun `test get item exist`() {
        app = API(port = 7000).init()
        Fuel.get("http://localhost:7000/api/items/0").response().let {
            println(it.first)
            println(it.second)
            println(it.third)

            TestCase.assertEquals(200, it.second.statusCode)
        }
        app.stop()
    }

    @Test
    fun `test get item not exist`() {
        app = API(port = 7000).init()
        Fuel.get("http://localhost:7000/api/items/-1").response().let {
            println(it.first)
            println(it.second)
            println(it.third)

            TestCase.assertEquals(404, it.second.statusCode)
        }
        app.stop()
    }
}