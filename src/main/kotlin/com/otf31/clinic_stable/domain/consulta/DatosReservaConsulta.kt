package com.otf31.clinic_stable.domain.consulta

import com.otf31.clinic_stable.domain.medico.Especialidad
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

@JvmRecord
data class DatosReservaConsulta(
  val idMedico: Long?,
  @NotNull
  val idPaciente: Long,
  @NotNull
  @Future
  val fecha: LocalDateTime,
  val especialidad: Especialidad?
)
