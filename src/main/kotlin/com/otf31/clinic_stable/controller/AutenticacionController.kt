package com.otf31.clinic_stable.controller

import com.otf31.clinic_stable.domain.usuarios.DatosAutenticacionUsuario
import com.otf31.clinic_stable.domain.usuarios.Usuario
import com.otf31.clinic_stable.infra.security.DatosJWTToken
import com.otf31.clinic_stable.infra.security.TokenService
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class AutenticacionController @Autowired constructor(
  private val tokenService: TokenService,
  private val authenticationManager: AuthenticationManager
) {
  @PostMapping
  fun autenticarUsuario(
    @Valid
    @RequestBody
    datosAutenticacionUsuario: DatosAutenticacionUsuario
  ): ResponseEntity<DatosJWTToken> {
    val authToken = UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.login, datosAutenticacionUsuario.clave)
    val usuarioAutenticado = authenticationManager.authenticate(authToken)
    val jwtToken = tokenService.generarToken(usuarioAutenticado.principal as Usuario)

    return ResponseEntity.ok(DatosJWTToken(jwtToken))
  }
}