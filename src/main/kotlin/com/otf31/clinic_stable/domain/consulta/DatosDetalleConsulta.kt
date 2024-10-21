package com.otf31.clinic_stable.domain.consulta

import com.otf31.clinic_stable.domain.consulta.Consulta
import java.time.LocalDateTime

@JvmRecord
data class DatosDetalleConsulta(
  val id: Long? = null,
  val idMedico: Long? = null,
  val idPaciente: Long? = null,
  val fecha: LocalDateTime? = null
) {
  companion object {
    fun from(consulta: Consulta): DatosDetalleConsulta {
      return DatosDetalleConsulta(
        id = consulta.id,
        idMedico = consulta.medico.id,
        idPaciente = consulta.paciente.id,
        fecha = consulta.fecha
      )
    }
  }
}
