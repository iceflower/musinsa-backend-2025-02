package com.musinsa.homework.components.lowprice.byallcategories.statement

import com.musinsa.homework.jpa.entities.category.vo.PriceByCategory

/**
 * 카테고리별 최저가 상품 브랜드 명세서 객체.
 *
 * @property totalPrice 합계 가격
 * @property detailed 카테고리별 브랜드 및 상품가격
 */
data class LowPriceByCategoriesStatement(
  val totalPrice: Long,
  val detailed: List<PriceByCategory>
) {

  companion object {

    /**
     * LowPriceByCategoriesStatement 를 생성하기 위한 정적 팩토리 메소드입니다.
     *
     * @param list 카테고리별 최저가 상품 리스트
     * @return LowPriceByCategoriesStatement
     */
    fun create(list: List<PriceByCategory>): LowPriceByCategoriesStatement {

      val totalPrice = list.distinctBy { it.categoryName }
        .sumOf { it.price }

      return LowPriceByCategoriesStatement(totalPrice, list)
    }
  }
}
