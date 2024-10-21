package com.otf31.clinic_stable.domain.medico

import com.otf31.clinic_stable.domain.direccion.DatosDireccion

@JvmRecord
data class DatosRespuestaMedico(
  val id: Long?,
  val nombre: String,
  val email: String,
  val telefono: String,
  val documento: String,
  val especialidad: String,
  val direccion: DatosDireccion
)