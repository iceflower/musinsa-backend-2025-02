package com.musinsa.homework.components.lowprice.bybrand

import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.springframework.beans.factory.annotation.Autowired

@ComponentUsingDataJpaTest
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
        assertTrue { result.lowestPrice.products.isNotEmpty() }
        assertTrue { result.lowestPrice.brandName.isNotEmpty() }

        result.lowestPrice.products.groupingBy { it.categoryName }
          .eachCount()
          .forEach { assertTrue { it.value >= 1 } }

        assertTrue { result.lowestPrice.totalPrice > 0 }
      }
    }
  }
}
