package com.otf31.clinic_stable.controller

import com.otf31.clinic_stable.domain.consulta.DatosDetalleConsulta
import com.otf31.clinic_stable.domain.consulta.DatosReservaConsulta
import com.otf31.clinic_stable.domain.consulta.ReservaDeConsultas
import com.otf31.clinic_stable.domain.medico.Especialidad
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import java.time.LocalDateTime
import kotlin.jvm.Throws

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest @Autowired constructor(
  private val mockMvc: MockMvc,
  private val datosReservaConsultaJson: JacksonTester<DatosReservaConsulta>,
  private val datosDetalleConsultaJson: JacksonTester<DatosDetalleConsulta>,
  @MockBean
  private val reservaDeConsultas: ReservaDeConsultas
) {
  @Throws(Exception::class)
  @Test
  @DisplayName("Debería devolver HTTP 400 cuando el request no tenga datos")
  @WithMockUser
  fun reservarEscenario1() {
    val response = mockMvc
      .perform(post("/consultas"))
      .andReturn().response

    assertThat(response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
  }

  @Throws(Exception::class)
  @Test
  @DisplayName("Debería devolver HTTP 200 cuando en el request se envíe un JSON válido")
  @WithMockUser
  fun reservarEscenario2() {
    val fecha = LocalDateTime.now().plusHours(1)
    val especialidad = Especialidad.GINECOLOGIA
    val datosConsulta = DatosDetalleConsulta(
      null,
      2L,
      5L,
      fecha
    )
    Mockito
      .`when`(reservaDeConsultas.reservar(any()))
      .thenReturn(datosConsulta)
    val response = mockMvc
      .perform(
        post("/consultas")
          .contentType(MediaType.APPLICATION_JSON)
          .content(
            datosReservaConsultaJson
              .write(
                  DatosReservaConsulta(
                  2L,
                  5L,
                  fecha,
                  especialidad
                )
              ).json
          )
      )
      .andReturn().response

    val jsonEsperado = datosDetalleConsultaJson
      .write(datosConsulta).json

    assertThat(response.status).isEqualTo(HttpStatus.OK.value())
    assertThat(response.contentAsString).isEqualTo(jsonEsperado)
  }
}