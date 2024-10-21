package com.otf31.clinic_stable.domain.usuarios

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails

interface UsuarioRepository: JpaRepository<Usuario, Long> {
  fun findByLogin(username: String): UserDetails
}