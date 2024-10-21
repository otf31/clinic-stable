package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.ValidacionException
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import org.springframework.stereotype.Component
import java.time.DayOfWeek

@Component
class ValidadorFuerarHorarioConsultas: ValidadorDeConsultas {
  override fun validar(datos: DatosReservaConsulta) {
    val fechaConsulta = datos.fecha
    val domingo = fechaConsulta.dayOfWeek.equals(DayOfWeek.SUNDAY)
    val horarioAntesDeAperturaClinica = fechaConsulta.hour < 7
    val horarioDespuesDeCierreClinica = fechaConsulta.hour > 18;

    if (domingo || horarioAntesDeAperturaClinica || horarioDespuesDeCierreClinica) {
      throw ValidacionException("No se puede reservar consultas en este horario")
    }
  }
}