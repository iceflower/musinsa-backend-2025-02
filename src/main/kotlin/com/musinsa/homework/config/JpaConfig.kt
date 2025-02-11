package com.musinsa.homework.config

import jakarta.persistence.EntityManagerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableJpaRepositories
@EnableTransactionManagement
class JpaConfig {

  @Bean
  fun transactionManager(entityManager: EntityManagerFactory): PlatformTransactionManager {
    return JpaTransactionManager(entityManager)
  }
}
