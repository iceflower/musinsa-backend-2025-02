package com.musinsa.homework.jpa.entities.category

import com.musinsa.homework.jpa.entities.category.vo.LowPriceByCategory
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, String>, CategoryRepositoryCustom

interface CategoryRepositoryCustom {
  /**
   * 카테고리별 최저가 상품 리스트를 조회하여 돌려줍니다.
   * @return 카테고리별 최저가 상품 리스트
   */
  fun lowestPriceByAllCategories(): List<LowPriceByCategory>
}
