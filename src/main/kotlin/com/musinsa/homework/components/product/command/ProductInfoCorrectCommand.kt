package com.musinsa.homework.components.product.command

/**
 * 제품 정보 수정 명령서.
 *
 * @property productId 수정대상 제품 ID
 * @property brandId 수정할 브랜드 ID 정보
 * @property categoryId 수정할 카테고리 ID 정보
 * @property productName 수정할 제품 이름
 * @property productPrice 수정할 제품가격
 * @property requester 요청자
 */
data class ProductInfoCorrectCommand(
  val productId: String,
  val brandId: String,
  val categoryId: String,
  val productName: String,
  val productPrice: Long,
  val requester: String
)
