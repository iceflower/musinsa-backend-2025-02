package com.musinsa.homework.jpa.entities.product.vo

/**
 * 제품 정보 객체.
 *
 * @property productId 제품 ID
 * @property productName 제품명
 * @property categoryId 카테고리ID
 * @property categoryName 카테고리명
 * @property productPrice 제품가격
 */
data class ProductInfo(
  val productId: String,
  val productName: String,
  val categoryId: String,
  val categoryName: String,
  val brandId: String,
  val brandName: String,
  val productPrice: Long
)
