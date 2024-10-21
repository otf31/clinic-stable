package com.otf31.clinic_stable.domain.direccion

import jakarta.validation.constraints.NotBlank

@JvmRecord
data class DatosDireccion(
  @NotBlank
  val calle: String,
  @NotBlank
  val distrito: String,
  @NotBlank
  val ciudad: String,
  @NotBlank
  val numero: String,
  @NotBlank
  val complemento: String
)
