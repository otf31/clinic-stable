package com.otf31.clinic_stable.domain.consulta

import com.otf31.clinic_stable.domain.consulta.Consulta
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface ConsultaRepository: JpaRepository<Consulta, Long> {
  fun existsByPacienteIdAndFechaBetween(idPaciente: Long, primerHorario: LocalDateTime, ultimoHorario: LocalDateTime): Boolean

  fun existsByMedicoIdAndFecha(idMedico: Long?, fecha: LocalDateTime): Boolean
}