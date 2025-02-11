package com.musinsa.homework.components.price


import com.musinsa.homework.components.price.statement.LowPriceByCategoriesStatement
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.jpa.entities.category.vo.LowPriceByCategory
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class LowestPriceByAllCategoriesInformant(
  private val categoryRepository: CategoryRepository
) {

  /**
   * 카테고리별 최저가 브랜드 정보를 조회하여 돌려줍니다.
   *
   * @return 카테고리별 최저가 브랜드 리스트
   */
  @RedisDistributedLock(key = "lowest-price-brand-by-categories", readOnly = true)
  fun getLowestPriceBrandByCategories(): LowPriceByCategoriesStatement {
    val result = categoryRepository.lowestPriceByAllCategories()

    return LowPriceByCategoriesStatement.create(result)
  }

}
