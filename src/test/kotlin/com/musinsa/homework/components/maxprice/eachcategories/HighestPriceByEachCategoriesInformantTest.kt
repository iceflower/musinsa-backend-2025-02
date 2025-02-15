package com.musinsa.homework.components.maxprice.eachcategories

import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired


@ComponentUsingDataJpaTest
@DisplayName("HighestPriceByEachCategoriesInformant 클래스의")
class HighestPriceByEachCategoriesInformantTest {

  @Autowired
  lateinit var categoryRepository: CategoryRepository

  lateinit var highestPriceByEachCategoriesInformant: HighestPriceByEachCategoriesInformant

  @BeforeEach
  fun init() {
    highestPriceByEachCategoriesInformant = HighestPriceByEachCategoriesInformant(categoryRepository)
  }

  @Nested
  @DisplayName("getHighestPriceByEachCategory(String) 메소드는")
  inner class DescribeOf_getHighestPriceByEachCategory_method {

    fun subject(categoryName: String) = highestPriceByEachCategoriesInformant.getHighestPriceByEachCategory(categoryName)

    @Nested
    @DisplayName("존재하지 않는 카테고리명이 주어지면")
    inner class ContextWith_non_existence_category_name {

      @Test
      @DisplayName("CategoryNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        assertThrows<CategoryNotFoundException> { subject("NONEXIST") }
      }
    }

    @Nested
    @DisplayName("존재하는 카테고리명이 주어지면")
    inner class ContextWith_existence_category_name {

      @Test
      @DisplayName("아무런 예외를 던지지 않고 결과를 돌려준다")
      fun it_returns_successfully() {
        val result = assertDoesNotThrow { subject("상의") }

        assertTrue { result.isNotEmpty() }
        result.forEach {
          assertTrue { it.categoryName.isNotEmpty() }
          assertTrue { it.brandName.isNotEmpty() }
          assertTrue { it.price > 0 }
        }
      }
    }
  }
}
