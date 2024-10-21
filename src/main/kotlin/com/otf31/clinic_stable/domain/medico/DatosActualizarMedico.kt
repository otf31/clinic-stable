package com.otf31.clinic_stable.domain.medico

import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import jakarta.validation.constraints.NotNull

@JvmRecord
data class DatosActualizarMedico(
  @NotNull
  val id: Long,
  val nombre: String,
  val documento: String,
  val direccion: DatosDireccion
)
