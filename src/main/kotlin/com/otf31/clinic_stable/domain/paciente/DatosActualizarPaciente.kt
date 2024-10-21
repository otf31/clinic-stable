package com.otf31.clinic_stable.domain.paciente

import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import jakarta.validation.constraints.NotNull

@JvmRecord
data class DatosActualizarPaciente(
  @NotNull
  val id: Long,
  val nombre: String,
  val telefono: String,
  val direccion: DatosDireccion
)
