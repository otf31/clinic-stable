package com.otf31.clinic_stable.domain.paciente

import com.otf31.clinic_stable.domain.direccion.Direccion
import com.otf31.clinic_stable.domain.paciente.DatosActualizarPaciente
import jakarta.persistence.*
import jakarta.validation.Valid

@Table(name = "pacientes")
@Entity(name = "Paciente")
data class Paciente(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long,
  var nombre: String,
  var email: String,
  var telefono: String,
  var documento: String,
  @Embedded
  var direccion: Direccion,
  var activo: Boolean?
) {
  constructor(datos: DatosRegistroPaciente) : this(
    0,
    datos.nombre,
    datos.email,
    datos.telefono,
    datos.documento,
    Direccion(datos.direccion),
    true
  )

  fun actualizarInformaciones(@Valid datos: DatosActualizarPaciente) {
      nombre = datos.nombre
      telefono = datos.telefono
      direccion = direccion.actualizarDatos(datos.direccion)
  }

  fun eliminar() {
    activo = false
  }
}
