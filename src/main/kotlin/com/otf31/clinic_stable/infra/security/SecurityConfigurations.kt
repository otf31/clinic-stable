package com.otf31.clinic_stable.infra.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfigurations @Autowired constructor(
  private val securityFilter: SecurityFilter
) {
  @Throws(Exception::class)
  @Bean
  fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
    httpSecurity
        .csrf { it
          .disable()
        }
        .cors { it
          .disable()
        }
        .authorizeHttpRequests { it
          .requestMatchers(HttpMethod.POST, "/login")
          .permitAll()
          .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**", "/error", "/hello")
          .permitAll()
          .anyRequest()
          .authenticated()
        }
        .sessionManagement { it
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()

  @Bean
  fun authenticationManager (
    authenticationConfiguration: AuthenticationConfiguration
  ): AuthenticationManager =
    authenticationConfiguration.authenticationManager


  @Bean
  fun passwordEncoder(): PasswordEncoder =
    BCryptPasswordEncoder()

}