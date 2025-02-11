package com.musinsa.homework.testUtil

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer

class RedisTestContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

  private val redisContainer: GenericContainer<*> = GenericContainer("redis:7.4.2-alpine")
    .withExposedPorts(6379)

  override fun initialize(ctx: ConfigurableApplicationContext) {
    redisContainer.start()

    TestPropertyValues.of(
      "spring.data.redis.host=${redisContainer.host}",
      "spring.data.redis.port=${redisContainer.getMappedPort(6379)}",
      "spring.data.redis.client-type=lettuce",
    ).applyTo(ctx.environment)
  }

}
