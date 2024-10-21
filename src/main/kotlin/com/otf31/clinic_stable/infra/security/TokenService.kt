package com.otf31.clinic_stable.infra.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import com.otf31.clinic_stable.domain.usuarios.Usuario
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class TokenService {
  @Value("\${api.security.secret}")
  private val apiSecret: String = "123456"

 fun generarToken(usuario: Usuario): String {
   try {
     val algorithm = Algorithm.HMAC256(apiSecret)

     return JWT.create()
       .withIssuer("clinic otf31")
       .withSubject(usuario.login)
       .withClaim("id", usuario.id)
       .withExpiresAt(generarFechaExpiracion())
       .sign(algorithm)
   } catch (e: JWTCreationException) {
     throw RuntimeException("Error al crear el token")
   }
 }

  fun getSubject(token: String): String? {
    try {
      val algorithm = Algorithm.HMAC256(apiSecret)
      val verifier = JWT.require(algorithm)
        .withIssuer("clinic otf31")
        .build()
        .verify(token)

      if (verifier.subject == null) {
        throw RuntimeException("Verifier inválido")
      }

      return verifier.subject
    } catch (e: JWTVerificationException) {
      println(e.toString())
      return null
    }
  }

  private fun generarFechaExpiracion(): Instant {
    return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"))
  }
}