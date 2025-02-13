package com.musinsa.homework.components.maxprice.eachcategories

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import kotlin.test.assertTrue


@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@DataJpaTest
@Import(KotlinJdslAutoConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        assertTrue { result.categoryName.isNotEmpty() }
        assertTrue { result.brandName.isNotEmpty() }
        assertTrue { result.price > 0 }
      }
    }
  }
}
