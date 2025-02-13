package com.musinsa.homework.components.product.command

/**
 * 제품 정보 삭제 명령서.
 *
 * @property productId 제품 ID
 * @property requester 요청자
 */
data class ProductInfoRemoveCommand(
  val productId: String,
  val requester: String
)
