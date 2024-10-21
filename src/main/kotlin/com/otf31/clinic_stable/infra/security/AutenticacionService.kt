package com.otf31.clinic_stable.infra.security

import com.otf31.clinic_stable.domain.usuarios.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AutenticacionService @Autowired constructor(private val usuarioRepository: UsuarioRepository) : UserDetailsService {

  @Throws(UsernameNotFoundException::class)
  override fun loadUserByUsername(username: String): UserDetails {
    return usuarioRepository.findByLogin(username)
  }
}