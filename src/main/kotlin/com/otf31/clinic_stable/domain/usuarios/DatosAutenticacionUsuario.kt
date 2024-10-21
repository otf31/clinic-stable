package com.otf31.clinic_stable.domain.usuarios

@JvmRecord
data class DatosAutenticacionUsuario(
  val login: String,
  val clave: String
)
