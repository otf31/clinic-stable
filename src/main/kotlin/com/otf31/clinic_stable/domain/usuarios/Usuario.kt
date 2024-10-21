package com.otf31.clinic_stable.domain.usuarios

import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Table(name = "usuarios")
@Entity(name = "Usuario")
data class Usuario(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long,
  val login: String,
  val clave: String,
): UserDetails {
  override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
    return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
  }

  override fun getPassword(): String =
    clave


  override fun getUsername(): String =
    login

}