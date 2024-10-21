package com.otf31.clinic_stable.domain.consulta

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.validaciones.ValidadorDeConsultas
import com.otf31.clinic_stable.domain.medico.Medico
import com.otf31.clinic_stable.domain.medico.MedicoRepository
import com.otf31.clinic_stable.domain.paciente.PacienteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ReservaDeConsultas @Autowired constructor(
  private val consultaRepository: ConsultaRepository,
  private val medicoRepository: MedicoRepository,
  private val pacienteRepository: PacienteRepository,
  private val validadores: List<ValidadorDeConsultas>
) {
  fun reservar(
    datos: DatosReservaConsulta
  ): DatosDetalleConsulta {

    if (pacienteRepository.existsById(datos.idPaciente).not()) {
      throw ValidacionException("No existe el paciente con id ${datos.idPaciente}")
    }

    if (datos.idMedico != null && medicoRepository.existsById(datos.idMedico).not()) {
      throw ValidacionException("No existe el médico con id ${datos.idMedico}")
    }

    validadores.forEach { it.validar(datos) }

    val medico = elegirMedico(datos) ?: throw ValidacionException("No se encontró un médico disponible en la fecha especificada")
    val paciente = pacienteRepository.findById(datos.idPaciente).orElseThrow()

    val consulta = Consulta(
      medico = medico,
      paciente = paciente,
      fecha = datos.fecha
    )

    consultaRepository.save(consulta)

    return DatosDetalleConsulta.from(consulta)
  }

  private fun elegirMedico(datos: DatosReservaConsulta): Medico? {
//    if (datos.idMedico != null) {
//      return medicoRepository.getReferenceById(datos.idMedico)
//    }
//
//    if (datos.especialidad == null) {
//      throw ValidacionException("Debe especificar la especialidad cuando no se especifica el médico")
//    }

    return when {
      datos.idMedico != null -> medicoRepository.getReferenceById(datos.idMedico)
      datos.especialidad == null -> throw ValidacionException("Debe especificar la especialidad cuando no se especifica el médico")
      else -> medicoRepository.elegirMedicoAleatorioDisponibleFecha(datos.especialidad, datos.fecha)
    }
  }
}