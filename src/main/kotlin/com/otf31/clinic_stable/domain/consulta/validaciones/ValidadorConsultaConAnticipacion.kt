package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import org.springframework.stereotype.Component
import java.time.Duration
import java.time.LocalDateTime

@Component
class ValidadorConsultaConAnticipacion: ValidadorDeConsultas {
  override fun validar(datos: DatosReservaConsulta) {
    val fechaConsulta = datos.fecha
    val ahora = LocalDateTime.now()
    val diferencia = Duration.between(ahora, fechaConsulta).toMinutes()

    if (diferencia < 30) {
      throw ValidacionException("La consulta debe ser reservada con al menos 30 minutos de anticipación")
    }
  }
}