package com.musinsa.homework.api.brand.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * 브랜드 삭제요청 객체.
 *
 * @property requester 요청자
 */
data class BrandInfoRemoveRequest(
  @field:NotNull("삭제요청자는 필수값입니다.")
  @field:NotEmpty("삭제요청자는 필수값입니다.")
  @field:NotBlank("삭제요청자는 필수값입니다.")
  val requester: String?
)
