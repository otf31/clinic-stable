package com.otf31.clinic_stable.domain.consulta

import com.otf31.clinic_stable.domain.medico.Medico
import com.otf31.clinic_stable.domain.paciente.Paciente
import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "consultas")
@Entity(name = "Consulta")
data class Consulta(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  val id: Long? = null,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "medico_id")
  val medico: Medico,

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "paciente_id")
  val paciente: Paciente,

  val fecha: LocalDateTime
)
