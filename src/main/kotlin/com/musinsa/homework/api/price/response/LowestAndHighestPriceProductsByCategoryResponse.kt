package com.musinsa.homework.api.price.response

/**
 * 카테고리별 최저가/최고가 제품 정보 객체.
 *
 * @property categoryName 카테고리명
 * @property lowestPriceList 최저가 제품 리스트
 * @property highestPriceList 최고가 제품 리스트
 */
data class LowestAndHighestPriceProductsByCategoryResponse(
  val categoryName: String,
  val lowestPriceList: List<ProductInfo>,
  val highestPriceList: List<ProductInfo>,
)

/**
 * 제품 정보 객체.
 *
 * @property brandName 브랜드명
 * @property price 제품가격
 */
data class ProductInfo(
  val brandName: String,
  val price: Long
)

