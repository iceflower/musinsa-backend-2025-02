plugins {
  kotlin("jvm") version "2.1.10"
  kotlin("plugin.spring") version "2.1.10"
  kotlin("plugin.jpa") version "2.1.10"
  id("org.springframework.boot") version "3.4.2"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "com.musinsa"
version = "0.0.1-SNAPSHOT"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  mavenCentral()
}

extra.apply {
  set("testcontainers-version", "1.20.4")
  set("hypersistence-utils-hibernate-version", "3.9.1")
  set("redisson-spring-boot-starter-version", "3.44.0")
  set("kotlin-jdsl-version", "3.5.4")
  set("springdoc-openapi-starter-webmvc-ui-version", "2.8.4")
}

val testcontainersVersion = rootProject.extra["testcontainers-version"]
val redissonSpringBootStarterVersion = rootProject.extra["redisson-spring-boot-starter-version"]
val hypersistenceUtilsHibernateVersion = rootProject.extra["hypersistence-utils-hibernate-version"]
val kotlinJdslVersion = rootProject.extra["kotlin-jdsl-version"]
val springdocOpenapiSterterWebmvcUiVersion = rootProject.extra["springdoc-openapi-starter-webmvc-ui-version"]
dependencies {
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.data:spring-data-redis")
  implementation("org.springframework.boot:spring-boot-starter-validation")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-aop")
  implementation("org.redisson:redisson-spring-boot-starter:$redissonSpringBootStarterVersion")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("io.hypersistence:hypersistence-utils-hibernate-63:$hypersistenceUtilsHibernateVersion")
  implementation("com.linecorp.kotlin-jdsl:jpql-dsl:$kotlinJdslVersion")
  implementation("com.linecorp.kotlin-jdsl:jpql-render:$kotlinJdslVersion")
  implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:$kotlinJdslVersion")
  implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocOpenapiSterterWebmvcUiVersion")

  runtimeOnly("com.h2database:h2")

  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.testcontainers:testcontainers:$testcontainersVersion")
  testImplementation("org.testcontainers:junit-jupiter:$testcontainersVersion")

  testRuntimeOnly("com.h2database:h2")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")

}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict")
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
