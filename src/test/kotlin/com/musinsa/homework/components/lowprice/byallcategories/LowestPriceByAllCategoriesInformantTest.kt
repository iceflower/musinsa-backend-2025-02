package com.musinsa.homework.components.lowprice.byallcategories

import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired

@ComponentUsingDataJpaTest
@DisplayName("LowestPriceByAllCategoriesInformant 클래스의")
class LowestPriceByAllCategoriesInformantTest {

  @Autowired
  lateinit var categoryRepository: CategoryRepository

  lateinit var lowestPriceByAllCategoriesInformant: LowestPriceByAllCategoriesInformant

  @BeforeEach
  fun init() {
    lowestPriceByAllCategoriesInformant = LowestPriceByAllCategoriesInformant(categoryRepository)
  }

  @Nested
  @DisplayName("getLowestPriceBrandByCategories() 메소드는")
  inner class DescribeOf_getLowestPriceBrandByCategories_method {

    fun subject() = lowestPriceByAllCategoriesInformant.getLowestPriceBrandByCategories()

    @Nested
    @DisplayName("메소드를 호출하면")
    inner class ContextWhen_function_call {

      @Test
      @DisplayName("카테고리별 최저가 브랜드 리스트를 돌려준다.")
      fun it_returns_list() {

        val result = assertDoesNotThrow { subject() }

        assertTrue { result.totalPrice > 0 }
        assertTrue { result.detailed.isNotEmpty() }

        result.detailed.groupingBy { it.categoryName }
          .eachCount()
          .forEach { assertTrue { it.value >= 1 } }

        // 원래대로라면 8개가 나와야 한다.
        // 다만, 테스트 데이터 중 최저가 제품이 2개인 카테고리가 존재함. (스니커즈)
        // 그래서 9개가 리턴된 것이므로 참고바람.
        assertEquals(9, result.detailed.size)

      }
    }
  }
}
