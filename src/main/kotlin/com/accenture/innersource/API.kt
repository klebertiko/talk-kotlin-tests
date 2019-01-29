package com.accenture.innersource

import io.javalin.Context
import io.javalin.Javalin
import io.javalin.UnauthorizedResponse
import io.javalin.apibuilder.ApiBuilder.get
import io.javalin.apibuilder.ApiBuilder.path
import io.javalin.apibuilder.ApiBuilder.post
import io.javalin.security.Role
import io.javalin.security.SecurityUtil
import kotlin.reflect.KFunction1

internal enum class Roles : Role {
    ANYONE, AUTHENTICATED
}

class API(private val port: Int) {

    private val controller = ItemController()
    private val authController = AuthController()

    fun init(): Javalin {

        val app = Javalin.create().apply {
            port(port)
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            accessManager { handler, ctx, permittedRoles ->
                val userRole = getUserRole(ctx)
                if (permittedRoles.contains(userRole)) {
                    handler.handle(ctx)
                } else {
                    throw UnauthorizedResponse()
                }
            }
        }.start()

        app.get("/", { ctx -> ctx.json(mapOf("messages" to messages)) }, SecurityUtil.roles(Roles.ANYONE))

        app.routes {
            path("api") {
                path("items") {
                    get(":id", { ctx -> controller.getItem(ctx) }, SecurityUtil.roles(Roles.ANYONE))
                }
                path("users") {
                    post("login", { ctx -> asJson(ctx, authController::login) }, SecurityUtil.roles(Roles.ANYONE))
                }
            }
        }
        return app
    }

    private fun getUserRole(ctx: Context): Role {
        val jwtToken = getTokenHeader(ctx)
        if (jwtToken.isNullOrBlank()) {
            return Roles.ANYONE
        }

        val userRole = JWTProvider.decodeJWT(jwtToken).getClaim("role").asString()

        return Roles.valueOf(userRole)
    }

    private fun getTokenHeader(ctx: Context): String? {
        return ctx.header(header = "Authorization")?.substringAfter(delimiter = "Token")?.trim()
    }

    private fun asJson(ctx: Context, handler: KFunction1<@ParameterName(name = "ctx") Context, Any>) {
        ctx.json(handler.call(ctx))
    }
}

fun main() {
    API(port = 7000).init()
}
