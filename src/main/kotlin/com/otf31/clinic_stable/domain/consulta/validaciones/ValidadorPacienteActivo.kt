package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import com.otf31.clinic_stable.domain.paciente.PacienteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ValidadorPacienteActivo @Autowired constructor(
  private val pacienteRepository: PacienteRepository
): ValidadorDeConsultas {
  override fun validar(datos: DatosReservaConsulta) {
    val pacienteEstaActivo = pacienteRepository.findActivoById(datos.idPaciente)

    if (pacienteEstaActivo.not()) {
      throw ValidacionException("El paciente no está activo, la consulta no puede ser reservada")
    }
  }
}