package com.accenture.innersource

import io.javalin.Context

class AuthController {

    fun login(ctx: Context): UserDTO {
        val userRequest = ctx.validatedBody<UserDTO>()
                .check({ !it.user.username.isNullOrBlank() })
                .check({ !it.user.password.isNullOrBlank() })
                .check({ !it.user.username.equals(users[0]!!.username) })
                .check({ !it.user.password.equals(users[0]!!.password) })
                .getOrThrow()
        return UserDTO(userRequest.user.copy(token = JWTProvider.createJWT(userRequest.user, Roles.AUTHENTICATED)))
    }
}

class ItemController {

    fun getItem(ctx: Context) {
        ctx.pathParam("id").toInt().let {
            items[it]?.let { item ->
                ctx.json(item)
                return
            }
            ctx.status(404)
        }
    }
}
