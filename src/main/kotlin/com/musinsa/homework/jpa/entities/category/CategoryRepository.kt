package com.musinsa.homework.jpa.entities.category

import com.musinsa.homework.jpa.entities.category.vo.PriceByCategory
import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, String>, CategoryRepositoryCustom {

  /**
   * 카테고리 이름이 주어지면, 카테고리 이름의 존재여부를 확인하여 그 결과를 돌려줍니다.
   *
   * @return 주어진 카테고리 이름이 주어지면 true, 존재하지 않으면 false
   */
  fun existsByName(name: String): Boolean
}

interface CategoryRepositoryCustom {

  /**
   * 카테고리별 최저가 제품 리스트를 조회하여 돌려줍니다.
   *
   * @return 카테고리별 최저가 제품 리스트
   */
  fun lowestPriceByAllCategories(): List<PriceByCategory>

  /**
   * 카테고리 이름이 주어지면, 그 카테고리의 제품 중 최저가 제품 정보를 조회하여 돌려줍니다.
   *
   * @param name 조회대상 카테고리의 이름
   * @return 조회대상 카테고리 내 최저가 제품의 브랜드 및 가격 정보
   */
  fun lowestPriceByEachCategory(name: String): List<PriceByCategory>

  /**
   * 카테고리 이름이 주어지면, 그 카테고리의 제품 중 최고가 제품 정보를 조회하여 돌려줍니다.
   *
   * @param name 조회대상 카테고리의 이름
   * @return 조회대상 카테고리 내 최고가 제품의 브랜드 및 가격 정보
   */
  fun highestPriceByEachCategory(name: String): List<PriceByCategory>
}
