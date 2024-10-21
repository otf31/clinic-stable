package com.otf31.clinic_stable.controller

import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import com.otf31.clinic_stable.domain.medico.*
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/medicos")
@SecurityRequirement(name = "bearer-key")
class MedicoController @Autowired constructor(
  private val medicoRepository: MedicoRepository
) {
  @PostMapping
  fun registrarMedico(
    @Valid
    @RequestBody
    datosRegistroMedico: DatosRegistroMedico,
    uriComponentsBuilder: UriComponentsBuilder
  ): ResponseEntity<DatosRespuestaMedico> {
    val medico = medicoRepository.save(Medico.from(datosRegistroMedico))
    val datosRespuestaMedico = DatosRespuestaMedico(
      medico.id,
      medico.nombre,
      medico.email,
      medico.telefono,
      medico.documento,
      medico.especialidad.name,
      DatosDireccion(
        medico.direccion.calle,
        medico.direccion.distrito,
        medico.direccion.ciudad,
        medico.direccion.numero,
        medico.direccion.complemento
      )
    )
    val url = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.id).toUri()

    return ResponseEntity.created(url).body(datosRespuestaMedico)
  }

  @GetMapping
  fun listadoMedicos(
    @PageableDefault(size = 2)
    paginacion: Pageable
  ): ResponseEntity<Page<DatosListadoMedico>> {
    //        return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
    return ResponseEntity.ok(medicoRepository.findByActivoTrue(paginacion).map { DatosListadoMedico.from(it) })
  }

  @PutMapping
  @Transactional
  fun actualizarMedico(
    @Valid
    @RequestBody
    datosActualizarMedico: DatosActualizarMedico
  ): ResponseEntity<*> {
    val medico: Medico = medicoRepository.getReferenceById(datosActualizarMedico.id)
    medico.actualizarDatos(datosActualizarMedico)

    return ResponseEntity.ok<Any>(
      DatosRespuestaMedico(
        medico.id,
        medico.nombre,
        medico.email,
        medico.telefono,
        medico.documento,
        medico.especialidad.name,
        DatosDireccion(
          medico.direccion.calle, medico.direccion.distrito,
          medico.direccion.ciudad, medico.direccion.numero,
          medico.direccion.complemento
        )
      )
    )
  }

  // DELETE LOGICO
  @DeleteMapping("/{id}")
  @Transactional
  fun eliminarMedico(
    @PathVariable
    id: Long
  ): ResponseEntity<*> {
    val medico: Medico = medicoRepository.getReferenceById(id)
    medico.desactivarMedico()
    return ResponseEntity.noContent().build<Any>()
  }

  @GetMapping("/{id}")
  fun retornaDatosMedico(
    @PathVariable
    id: Long
  ): ResponseEntity<DatosRespuestaMedico> {
    val medico: Medico = medicoRepository.getReferenceById(id)
    val datosMedico = DatosRespuestaMedico(
      medico.id,
      medico.nombre,
      medico.email,
      medico.telefono,
      medico.documento,
      medico.especialidad.name,
      DatosDireccion(
        medico.direccion.calle, medico.direccion.distrito,
        medico.direccion.ciudad, medico.direccion.numero,
        medico.direccion.complemento
      )
    )

    return ResponseEntity.ok(datosMedico)
  }
}