package com.musinsa.homework.components.price.statement

/**
 * 특정 브랜드의 제품만 구매했을 때, 최저가로 구매 가능한 브랜드 및 그 브랜드의 제품 정보 명세서 객체.
 *
 * @property brandName 브랜드명
 * @property totalPrice 제품 가격 합계
 * @property brandProductsByCategories 카테고리별 제품 정보
 */
data class LowPriceBrandStatement(
  val brandName: String,
  val totalPrice: Long,
  val brandProductsByCategories: List<ProductStatement>,
) {

  /**
   * second constructor.
   *
   * @param brandName 브랜드명
   * @param brandProductsByCategories 카테고리별 제품 정보
   */
  constructor(brandName: String, brandProductsByCategories: List<ProductStatement>)
    : this(brandName, brandProductsByCategories.sumOf { it.price }, brandProductsByCategories)
}

/**
 * 카테고리별 제품 명세서 객체.
 *
 * @property categoryName 카테고리명
 * @property price 제품 가격
 */
data class ProductStatement(
  val categoryName: String,
  val price: Long
)


