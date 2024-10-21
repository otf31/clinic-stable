package com.otf31.clinic_stable.infra.security

import com.otf31.clinic_stable.domain.usuarios.UsuarioRepository
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class SecurityFilter @Autowired constructor(
  private val tokenService: TokenService,
  private val usuarioRepository: UsuarioRepository
) : OncePerRequestFilter() {

  @Throws(ServletException::class, IOException::class)
  override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
    val authHeader = request.getHeader("Authorization")

    if (authHeader != null) {
      val token = authHeader.replace("Bearer ", "")
      val nombreUsuario = tokenService.getSubject(token)

      if (nombreUsuario != null) {
        val usuario = usuarioRepository.findByLogin(nombreUsuario)
        val authentication = UsernamePasswordAuthenticationToken(usuario, null, usuario.authorities)

        SecurityContextHolder.getContext().authentication = authentication
      }
    }

    filterChain.doFilter(request, response)
  }
}