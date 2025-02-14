package com.musinsa.homework.api.product.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.PositiveOrZero

data class ProductInfoCorrectRequest(
  @field:NotNull("브랜드ID는 필수값입니다.")
  @field:NotEmpty("브랜드ID는 필수값입니다.")
  @field:NotBlank("브랜드ID는 필수값입니다.")
  val brandId: String?,

  @field:NotNull("카테고리ID는 필수값입니다.")
  @field:NotEmpty("카테고리ID는 필수값입니다.")
  @field:NotBlank("카테고리ID는 필수값입니다.")
  val categoryId: String?,

  @field:NotNull("제품명은 필수값입니다.")
  @field:NotEmpty("제품명은 필수값입니다.")
  @field:NotBlank("제품명은 필수값입니다.")
  val productName: String?,

  @field:NotNull("제품가격은 필수값입니다.")
  @field:PositiveOrZero("제품가격은 0 이상의 값이어야 합니다.")
  val productPrice: Long?,

  @field:NotNull("수정요청자는 필수값입니다.")
  @field:NotEmpty("수정요청자는 필수값입니다.")
  @field:NotBlank("수정요청자는 필수값입니다.")
  val requester: String?
)
