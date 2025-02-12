package com.musinsa.homework.components.price

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import kotlin.test.Test
import kotlin.test.assertTrue


@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@DataJpaTest
@Import(KotlinJdslAutoConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("LowestPriceBrandInformant 클래스의")
class LowestPriceBrandInformantTest {

  @Autowired
  lateinit var brandRepository: BrandRepository
  @Autowired
  lateinit var productRepository: ProductRepository

  lateinit var lowestPriceBrandInformant: LowestPriceBrandInformant

  @BeforeEach
  fun init() {
    lowestPriceBrandInformant = LowestPriceBrandInformant(brandRepository, productRepository)
  }

  @Nested
  @DisplayName("getLowPriceBrandInfo() 메소드는")
  inner class Describe_of_getLowPriceBrandInfo_method {
    fun subject() = lowestPriceBrandInformant.getLowPriceBrandInfo()

    @Nested
    @DisplayName("caller에 의해 호출되면")
    inner class ContexWhen_called_by_caller {

      @Test
      @DisplayName("최저가로 구매 가능한 브랜드의 제품 리스트 정보를 돌려준다")
      fun it_returns_value() {

        val result = assertDoesNotThrow { subject() }

        assertTrue { result.brandProductsByCategories.isNotEmpty() }
        assertTrue { result.brandName.isNotEmpty() }
        assertTrue { result.totalPrice > 0 }

      }
    }
  }
}
