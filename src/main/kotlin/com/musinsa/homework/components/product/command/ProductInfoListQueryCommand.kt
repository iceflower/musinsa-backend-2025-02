package com.musinsa.homework.components.product.command

/**
 * 제품 리스트 조회 명령서 객체.
 *
 * @property brandId 브랜드명
 * @property categoryId 카테고리명
 * @property priceFrom 제품 가격 조건 - from
 * @property priceTo 제품 가격 조건 - to
 */
data class ProductInfoListQueryCommand(
  val brandId: String?,
  val categoryId: String?,
  val priceFrom: Long?,
  val priceTo: Long?,
)
