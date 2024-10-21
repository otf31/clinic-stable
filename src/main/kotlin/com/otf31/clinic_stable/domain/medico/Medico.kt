package com.otf31.clinic_stable.domain.medico

import com.otf31.clinic_stable.domain.direccion.Direccion
import jakarta.persistence.*

@Table(name = "medicos")
@Entity(name = "Medico")
data class Medico(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,
  var nombre: String,
  var email: String,
  val telefono: String,
  var documento: String,
  var activo: Boolean,
  @Enumerated(EnumType.STRING)
  val especialidad: Especialidad,
  @Embedded
  var direccion: Direccion
) {
  companion object {
    fun from(datos: DatosRegistroMedico): Medico =
      Medico(
        null,
        datos.nombre,
        datos.email,
        datos.telefono,
        datos.documento,
        true,
        datos.especialidad,
        Direccion(datos.direccion)
      )
  }

  fun actualizarDatos(datos: DatosActualizarMedico) {
    nombre = datos.nombre
    documento = datos.documento
    direccion = direccion.actualizarDatos(datos.direccion)
  }

  fun desactivarMedico() {
    activo = false
  }
}