package com.otf31.clinic_stable.domain.paciente

@JvmRecord
data class DatosListadoPaciente(
  val id: Long,
  val nombre: String,
  val email: String,
  val documento: String
) {
  constructor(paciente: Paciente): this(
    paciente.id,
    paciente.nombre,
    paciente.email,
    paciente.documento
  )
}