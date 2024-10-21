package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.ConsultaRepository
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ValidadorMedicoConOtraConsultaEnElMismoHorario @Autowired constructor(
  private val consultaRepository: ConsultaRepository
): ValidadorDeConsultas {
  override fun validar(datos: DatosReservaConsulta) {
    val medicoTieneOtraConsultaEnElMismoHorario = consultaRepository.existsByMedicoIdAndFecha(datos.idMedico, datos.fecha)

    if (medicoTieneOtraConsultaEnElMismoHorario) {
      throw ValidacionException("El médico ya tiene una consulta reservada para el mismo horario")
    }
  }
}