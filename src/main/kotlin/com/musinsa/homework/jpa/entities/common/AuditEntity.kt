package com.musinsa.homework.jpa.entities.common

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import java.time.LocalDateTime

@MappedSuperclass
abstract class AuditEntity() {

  @Column(name = "created_at", nullable = false)
  lateinit var createdAt: LocalDateTime

  @Column(name = "created_by", nullable = false)
  lateinit var createdBy: String

  @Column(name = "updated_at", nullable = false)
  lateinit var updatedAt: LocalDateTime

  @Column(name = "updated_by", nullable = false)
  lateinit var updatedBy: String
}
