package com.otf31.clinic_stable.controller

import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import com.otf31.clinic_stable.domain.medico.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.DisplayName
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import kotlin.jvm.Throws

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class MedicoControllerTest @Autowired constructor(
  private val mockMvc: MockMvc,
  private val datosRegistroMedicoJson: JacksonTester<DatosRegistroMedico>,
  private val datosRespuestaMedicoJson: JacksonTester<DatosRespuestaMedico>,
  @MockBean
  private val medicoRepository: MedicoRepository
) {
  @Throws(Exception::class)
  @Test
  @DisplayName("Debería devolver HTTP 400 cuando el request no tenga datos")
  @WithMockUser
  fun registrarMedicoEscenario1() {
    val response = mockMvc
      .perform(post("/medicos"))
      .andReturn().response

    assertThat(response.status).isEqualTo(HttpStatus.BAD_REQUEST.value())
  }

  @Throws(Exception::class)
  @Test
  @DisplayName("Debería devolver HTTP 201 cueando en el request se envíe un JSON válido")
  @WithMockUser
  fun registrarMedicoEscenario2() {
    val datosRegistro = DatosRegistroMedico(
      "Juan Perez",
      "juan.perez@gmail.com",
      "123456789",
      "123456",
      Especialidad.GINECOLOGIA,
      DatosDireccion(
        "Calle 123",
        "Distrito 1",
        "Ciudad 1",
        "123",
        "Complemento 1"
      ),
    )
    Mockito.`when`(medicoRepository.save(any()))
      .thenReturn(Medico.from(datosRegistro))

    val response = mockMvc
      .perform(
        post("/medicos")
          .contentType("application/json")
          .content(datosRegistroMedicoJson.write(datosRegistro).json)
      )
      .andReturn().response

    val datosRespuesta = DatosRespuestaMedico(
      null,
      datosRegistro.nombre,
      datosRegistro.email,
      datosRegistro.telefono,
      datosRegistro.documento,
      datosRegistro.especialidad.name,
      datosRegistro.direccion
    )

    val jsonEsperado = datosRespuestaMedicoJson
      .write(datosRespuesta).json

    assertThat(response.status).isEqualTo(HttpStatus.CREATED.value())
    assertThat(response.contentAsString).isEqualTo(jsonEsperado)
  }
}