package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import com.otf31.clinic_stable.domain.medico.MedicoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ValidadorMedicoActivo @Autowired constructor(
  private val medicoRepository: MedicoRepository
): ValidadorDeConsultas {
  override fun validar(datos: DatosReservaConsulta) {
    if (datos.idMedico == null){
      return
    }

    val medicoEstaActivo = medicoRepository.findActivoById(datos.idMedico)

    if (medicoEstaActivo.not()) {
      throw ValidacionException("El médico no está activo, la consulta no puede ser reservada")
    }
  }
}