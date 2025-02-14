package com.musinsa.homework.api.brand.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

/**
 * 제품정보 등록요청 객체.
 *
 * @property brandName
 * @property requester
 */
data class BrandInfoRegisterRequest(
  @field:NotNull("브랜드명은 필수값입니다.")
  @field:NotEmpty("브랜드명은 필수값입니다.")
  @field:NotBlank("브랜드명은 필수값입니다.")
  val brandName: String?,

  @field:NotNull("등록요청자는 필수값입니다.")
  @field:NotEmpty("등록요청자는 필수값입니다.")
  @field:NotBlank("등록요청자는 필수값입니다.")
  val requester: String?
)
