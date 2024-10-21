package com.otf31.clinic_stable.infra.errores

import com.otf31.clinic_stable.domain.ValidacionException
import jakarta.persistence.EntityNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class TratadorDeErrores {
  @ExceptionHandler(EntityNotFoundException::class)
  fun tratarError404(): ResponseEntity<Any> =
    ResponseEntity.notFound().build()

  @ExceptionHandler(MethodArgumentNotValidException::class)
  fun tratarError400(
    e: MethodArgumentNotValidException
  ): ResponseEntity<List<DatosErrorValidacion>> {
    val errores = e.fieldErrors.map { DatosErrorValidacion.from(it) }

    return ResponseEntity.badRequest().body(errores)
  }

  @ExceptionHandler(ValidacionException::class)
  fun tratarErrorDeValidacion(
    e: ValidacionException
  ): ResponseEntity<String> =
    ResponseEntity.badRequest().body(e.message)

  @JvmRecord
  data class DatosErrorValidacion(
    val campo: String,
    val error: String?
  ) {
    companion object {
      fun from(error: FieldError): DatosErrorValidacion =
        DatosErrorValidacion(error.field, error.defaultMessage)
    }
  }
}