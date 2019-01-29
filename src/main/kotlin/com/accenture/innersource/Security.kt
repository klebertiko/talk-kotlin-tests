package com.accenture.innersource

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import io.javalin.security.Role

object JWTProvider {

    private val algorithm = Algorithm.HMAC256("something-very-secret-here")

    fun verify(issuer: String): JWTVerifier {
        return JWT.require(algorithm).withIssuer(issuer).build()
    }

    fun decodeJWT(token: String): DecodedJWT {
        return JWT.require(algorithm).build().verify(token)
    }

    fun createJWT(user: User, role: Role): String? {
        return JWT.create()
                .withClaim("username", user.username)
                .withClaim("role", role.toString())
                .sign(algorithm)
    }
}
