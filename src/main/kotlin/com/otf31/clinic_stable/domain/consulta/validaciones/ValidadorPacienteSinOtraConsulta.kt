package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.ConsultaRepository
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ValidadorPacienteSinOtraConsulta @Autowired constructor(
  private val consultaRepository: ConsultaRepository
): ValidadorDeConsultas {
  override fun validar(datos: DatosReservaConsulta) {
    val primerHorario = datos.fecha.withHour(7)
    val ultimoHorario = datos.fecha.withHour(18)
    val pacienteTieneOtraConsultaEnElDia = consultaRepository.existsByPacienteIdAndFechaBetween(datos.idPaciente, primerHorario, ultimoHorario)

    if (pacienteTieneOtraConsultaEnElDia) {
      throw ValidacionException("El paciente ya tiene una consulta reservada este día ${datos.fecha}")
    }
  }
}