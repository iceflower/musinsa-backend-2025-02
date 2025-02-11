package com.musinsa.homework.jpa.entities.category.vo

/**
 * 카테고리별 최저가 정보 객체.
 *
 * @property categoryName 카테고리명
 * @property brandName 브랜드 이름
 * @property price 가격
 */
data class LowPriceByCategory(
  val categoryName: String,
  val brandName: String,
  val price: Long
)
