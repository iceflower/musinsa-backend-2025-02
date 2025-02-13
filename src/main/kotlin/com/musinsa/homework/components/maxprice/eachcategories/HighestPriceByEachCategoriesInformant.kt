package com.musinsa.homework.components.maxprice.eachcategories

import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.jpa.entities.category.vo.PriceByCategory
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class HighestPriceByEachCategoriesInformant(
  private val categoryRepository: CategoryRepository
) {

  /**
   * 카테고리 이름이 주어지면, 그 카테고리 내 최고가 제품 정보를 조회하여 돌려줍니다.
   *
   * @param categoryName 조회대상 카테고리 이름
   * @return 조회대상 카테고리 내, 최고가  제품 정보
   */
  @RedisDistributedLock(key = "get-highest-price-by-category", readOnly = true)
  fun getHighestPriceByEachCategory(categoryName: String): PriceByCategory {

    if (!categoryRepository.existsByName(categoryName)) {
      throw CategoryNotFoundException("카테고리를 찾을 수 없습니다.")
    }

    return categoryRepository.highestPriceByEachCategory(categoryName)!!
  }
}
