package com.otf31.clinic_stable.domain.medico

import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import jakarta.validation.Valid
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern

@JvmRecord
data class DatosRegistroMedico(
  @NotBlank
  val nombre: String,
  @NotBlank
  @Email
  val email: String,
  @NotBlank
  val telefono: String,
  @NotBlank
  @Pattern(regexp = "\\d{4,6}")
  val documento: String,
  @NotNull
  val especialidad: Especialidad,
  @NotNull
  @Valid
  val direccion: DatosDireccion
)
