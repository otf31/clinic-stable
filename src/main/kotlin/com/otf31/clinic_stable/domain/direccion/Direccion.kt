package com.otf31.clinic_stable.domain.direccion

import jakarta.persistence.Embeddable

@Embeddable
data class Direccion(
  val calle: String,
  val numero: String,
  val complemento: String,
  val distrito: String,
  val ciudad: String,
) {
  constructor(direccion: DatosDireccion) : this(
    direccion.calle,
    direccion.numero,
    direccion.complemento,
    direccion.distrito,
    direccion.ciudad
  )

  fun actualizarDatos(direccion: DatosDireccion): Direccion {
    direccion.calle
    direccion.numero
    direccion.complemento
    direccion.distrito
    direccion.ciudad

    return this
  }
}