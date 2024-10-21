package com.otf31.clinic_stable.infra.springdoc

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringDocConfiguration {
  @Bean
  fun customOpenAPI(): OpenAPI =
    OpenAPI()
      .info(
        Info()
          .title("Clinic Stable API")
          .description("API to manage clinic reservations")
          .contact(
            Contact()
              .name("otf31")
              .email("otf31.351663@gmail.com")
          )
          .version("1.0")
      )
      .components(
        Components()
          .addSecuritySchemes(
            "bearer-key",
            SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
          )
      )
}