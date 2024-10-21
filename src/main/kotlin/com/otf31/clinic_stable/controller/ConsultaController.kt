package com.otf31.clinic_stable.controller

import com.otf31.clinic_stable.domain.consulta.DatosDetalleConsulta
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import com.otf31.clinic_stable.domain.consulta.ReservaDeConsultas
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
class ConsultaController @Autowired constructor(
  private val reserva: ReservaDeConsultas
) {
  @PostMapping
  @Transactional
  fun reservar(
    @Valid
    @RequestBody
    datos: DatosReservaConsulta
  ): ResponseEntity<DatosDetalleConsulta> {
    val datosDetalleConsulta = reserva.reservar(datos)

    return ResponseEntity.ok(datosDetalleConsulta)
  }
}