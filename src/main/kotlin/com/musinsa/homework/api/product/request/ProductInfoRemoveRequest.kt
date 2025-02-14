package com.musinsa.homework.api.product.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class ProductInfoRemoveRequest(
  @field:NotNull("삭제요청자는 필수값입니다.")
  @field:NotEmpty("삭제요청자는 필수값입니다.")
  @field:NotBlank("삭제요청자는 필수값입니다.")
  val requester: String?
)
