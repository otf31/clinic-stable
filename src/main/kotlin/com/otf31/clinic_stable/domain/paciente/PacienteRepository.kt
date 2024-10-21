package com.otf31.clinic_stable.domain.paciente

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface PacienteRepository: JpaRepository<Paciente, Long>{
  fun findAllByActivoTrue(
    paginacion: Pageable
  ): Page<Paciente>

  @Query("""
    SELECT p.activo
    FROM Paciente p
    WHERE p.id = :idPaciente
  """
  )
  fun findActivoById(
    idPaciente: Long
  ): Boolean
}