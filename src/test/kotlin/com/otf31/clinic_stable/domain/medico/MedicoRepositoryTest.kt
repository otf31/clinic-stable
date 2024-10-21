package com.otf31.clinic_stable.domain.medico

import com.otf31.clinic_stable.domain.consulta.Consulta
import com.otf31.clinic_stable.domain.direccion.DatosDireccion
import com.otf31.clinic_stable.domain.paciente.DatosRegistroPaciente
import com.otf31.clinic_stable.domain.paciente.Paciente
import jakarta.persistence.EntityManager
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest @Autowired constructor(
  private val medicoRepository: MedicoRepository,
  private val entityManager: EntityManager
) {
  @Test
  @DisplayName("Debería devolver null cuando el médico buscado existe pero no está disponible en esa fecha")
  fun elegirMedicoAleatorioDisponibleFechaEscenario1() {
    // Given
    val lunesSiguiente10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0)
    val medico = registrarMedico("Dr. House", "drhouse.gmail.com", "123456", Especialidad.CARDIOLOGIA)
    val paciente = registrarPaciente("Homero Simpson", "paciente@gmail.com", "987654321")

    registrarConsulta(medico, paciente, lunesSiguiente10)

    // When
    val medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleFecha(
      Especialidad.CARDIOLOGIA,
      lunesSiguiente10
    )

    // Then
    assertThat(medicoLibre).isNull()
  }

  @Test
  @DisplayName("Debería devolver médico cuando el médico buscado está disponible en esa fecha")
  fun elegirMedicoAleatorioDisponibleFechaEscenario2() {
    // Given
    val lunesSiguiente10 = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0)
    val medico = registrarMedico("Dr. House", "drhouse.gmail.com", "123456", Especialidad.CARDIOLOGIA)

    // When
    val medicoLibre = medicoRepository.elegirMedicoAleatorioDisponibleFecha(
      Especialidad.CARDIOLOGIA,
      lunesSiguiente10
    )

    // Then
    assertThat(medicoLibre).isEqualTo(medico)
  }

  @Test
  @DisplayName("Debería devolver true si el médico está activo")
  fun findActivoByIdEscenario1() {
    // Given
    val medico = registrarMedico("Dr. House", "drhouse.gmail.com", "123456", Especialidad.GINECOLOGIA)

    // When
    val activo = medicoRepository.findActivoById(medico.id)

    // Then
    assertThat(activo).isTrue()
  }

  @Test
  @DisplayName("Debería devolver false si el médico no está activo")
  fun findActivoByIdEscenario2() {
    // Given
    val medico = registrarMedico("Dr. House", "drhouse.gmail.com", "123456", Especialidad.GINECOLOGIA)

    // When
    medico.desactivarMedico()
    val activo = medicoRepository.findActivoById(medico.id)

    // Then
    assertThat(activo).isFalse()
  }

  private fun registrarConsulta(
    medico: Medico,
    paciente: Paciente,
    fecha: LocalDateTime
  ): Consulta {
    val consulta = Consulta(null, medico, paciente, fecha)
    entityManager.persist(consulta)

    return consulta
  }

  private fun registrarMedico(
    nombre: String,
    email: String,
    documento: String,
    especialidad: Especialidad
  ): Medico {
    val medico = Medico.from(datosMedico(nombre, email, documento, especialidad))
    entityManager.persist(medico)

    return medico
  }

  private fun registrarPaciente(
    nombre: String,
    email: String,
    documento: String
  ): Paciente {
    val paciente = Paciente(datosPaciente(nombre, email, documento))
    entityManager.persist(paciente)

    return paciente
  }

  private fun datosMedico(
    nombre: String,
    email: String,
    documento: String,
    especialidad: Especialidad
  ): DatosRegistroMedico =
    DatosRegistroMedico(
      nombre,
      email,
      "123456789",
      documento,
      especialidad,
      datosDireccion()
  )

  private fun datosPaciente(
    nombre: String,
    email: String,
    documento: String
  ): DatosRegistroPaciente =
    DatosRegistroPaciente(
      nombre,
      email,
      "123456789",
      documento,
      datosDireccion()
    )

  private fun datosDireccion(): DatosDireccion =
    DatosDireccion(
      "Calle Falsa 123",
      "Springfield",
      "Springfield",
      "742",
      "742"
    )
}