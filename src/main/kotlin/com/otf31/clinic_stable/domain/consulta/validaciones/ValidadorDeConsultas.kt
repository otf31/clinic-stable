package com.otf31.clinic_stable.domain.consulta.validaciones

import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta

interface ValidadorDeConsultas {
  fun validar(datos: DatosReservaConsulta)
}