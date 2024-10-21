package com.otf31.clinic_stable.controller

import com.otf31.clinic_stable.domain.paciente.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/pacientes")
@SecurityRequirement(name = "bearer-key")
class PacienteController @Autowired constructor(
  private val pacienteRepository: PacienteRepository
) {
  @PostMapping
  @Transactional
  fun registrar(
    @Valid
    @RequestBody
    datos: DatosRegistroPaciente
  ) {
    pacienteRepository.save(Paciente(datos))
  }

  @GetMapping
  fun listar(
    @PageableDefault(size = 10, sort = ["nombre"])
    paginacion: Pageable
  ): Page<DatosListadoPaciente> {
    return pacienteRepository.findAllByActivoTrue(paginacion).map { DatosListadoPaciente(it) }
  }

  @PutMapping
  @Transactional
  fun actualizar(
    @Valid
    @RequestBody
    datos: DatosActualizarPaciente
  ) {
    val paciente = pacienteRepository.getReferenceById(datos.id)

    paciente.actualizarInformaciones(datos)
  }

  @DeleteMapping("/{id}")
  @Transactional
  fun eliminar(
    @PathVariable
    id: Long
  ) {
    val paciente = pacienteRepository.getReferenceById(id)

    paciente.eliminar()
  }
}