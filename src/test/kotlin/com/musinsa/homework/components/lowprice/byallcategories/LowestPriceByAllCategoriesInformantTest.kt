package com.musinsa.homework.components.lowprice.byallcategories

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@DataJpaTest
@Import(KotlinJdslAutoConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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

        // 원래대로라면 8개가 나와야 한다.
        // 다만, 테스트 데이터 중 최저가 제품이 2개인 카테고리가 존재함. (스니커즈)
        // 그래서 9개가 리턴된 것이므로 참고바람.
        assertEquals(9, result.detailed.size)

      }
    }
  }
}
