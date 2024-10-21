package com.otf31.clinic_stable.domain.medico

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface MedicoRepository: JpaRepository<Medico, Long> {
  fun findByActivoTrue(paginacion: Pageable): Page<Medico>

  @Query("""
    SELECT m
    FROM Medico m
    WHERE m.activo = TRUE
      AND m.especialidad = :especialidad
      AND m.id not in (
        SELECT c.medico.id
        FROM Consulta c
        WHERE c.fecha = :fecha
      )
    ORDER BY RAND()
    LIMIT 1
  """)
  fun elegirMedicoAleatorioDisponibleFecha(
    especialidad: Especialidad,
    fecha: LocalDateTime
  ): Medico?

  @Query("""
    SELECT m.activo
    FROM Medico m
    WHERE m.id = :idMedico
  """)
  fun findActivoById(idMedico: Long?): Boolean
}