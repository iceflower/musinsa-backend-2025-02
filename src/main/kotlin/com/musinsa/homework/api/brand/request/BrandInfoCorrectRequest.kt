package com.musinsa.homework.api.brand.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * 제품정보 수정 요청 객체.
 *
 * @property brandName 브랜드명
 * @property requester 요청자
 */
data class BrandInfoCorrectRequest(
  @field:NotNull("브랜드명은 필수값입니다.")
  @field:NotEmpty("브랜드명은 필수값입니다.")
  @field:NotBlank("브랜드명은 필수값입니다.")
  val brandName: String?,

  @field:NotNull("수정요청자는 필수값입니다.")
  @field:NotEmpty("수정요청자는 필수값입니다.")
  @field:NotBlank("수정요청자는 필수값입니다.")
  val requester: String?
)
