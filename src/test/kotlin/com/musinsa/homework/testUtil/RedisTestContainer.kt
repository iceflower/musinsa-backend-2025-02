package com.musinsa.homework.testUtil

import org.springframework.test.context.ContextConfiguration

@Target(allowedTargets = [AnnotationTarget.TYPE, AnnotationTarget.CLASS])
@Retention(value = AnnotationRetention.RUNTIME)
@ContextConfiguration(initializers = [RedisTestContainerInitializer::class])
annotation class RedisTestContainer
