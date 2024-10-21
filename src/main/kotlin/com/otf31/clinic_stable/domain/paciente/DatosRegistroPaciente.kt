package com.otf31.clinic_stable.domain.paciente

import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

@JvmRecord
data class DatosRegistroPaciente(
  @NotBlank
  val nombre: String,
  @NotBlank
  @Email
  val email: String,
  @NotBlank
  val telefono: String,
  @NotBlank
  val documento: String,
  @NotNull
  @Valid
  val direccion: DatosDireccion
)
