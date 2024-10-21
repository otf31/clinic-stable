package com.otf31.clinic_stable.domain.medico

@JvmRecord
data class DatosListadoMedico(
  val id: Long?,
  val nombre: String,
  val especialidad: String,
  val documento: String,
  val email: String
) {
  companion object {
    fun from(medico: Medico): DatosListadoMedico =
      DatosListadoMedico(
        medico.id,
        medico.nombre,
        medico.especialidad.toString(),
        medico.documento,
        medico.email
      )
  }
}
