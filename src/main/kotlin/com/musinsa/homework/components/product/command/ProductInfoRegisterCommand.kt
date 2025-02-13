package com.musinsa.homework.components.product.command

/**
 * 제품 정보 등록 명령서.
 *
 * @property brandId 브랜드 ID
 * @property categoryId 카테고리 ID
 * @property productName 제품 이름
 * @property productPrice 제품가격
 * @property requester 요청자
 */
data class ProductInfoRegisterCommand(
  val brandId: String,
  val categoryId: String,
  val productName: String,
  val productPrice: Long,
  val requester: String
)
