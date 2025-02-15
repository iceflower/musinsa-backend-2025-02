package com.musinsa.homework.components.product

import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoCorrectCommand
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import kotlin.test.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired


@ComponentUsingDataJpaTest
@DisplayName("ProductInfoCorrector 클래스의")
class ProductInfoCorrectorTest {

  @Autowired
  lateinit var brandRepository: BrandRepository

  @Autowired
  lateinit var categoryRepository: CategoryRepository

  @Autowired
  lateinit var productRepository: ProductRepository

  lateinit var productInfoCorrector: ProductInfoCorrector

  @BeforeEach
  fun init() {
    productInfoCorrector = ProductInfoCorrector(
      brandRepository,
      categoryRepository,
      productRepository
    )
  }

  @Nested
  @DisplayName("correct(ProductInfoCorrectCommand) 메소드는")
  inner class DescribeOf_correct_method {
    fun subject(command: ProductInfoCorrectCommand) = productInfoCorrector.correct(command)

    @Nested
    @DisplayName("존재하지 않는 제품 ID가 주어지면")
    inner class ContextWith_nonexist_product_id {

      @Test
      @DisplayName("ProductNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        val command = ProductInfoCorrectCommand(
          "NONEXIST",
          "0JS975W4891XQ", // 'A' 브랜드
          "0JS97BHFR9353", // '상의' 카테고리
          "수정된 상의",
          10000,
          "test"
        )
        assertThrows<ProductNotFoundException> { subject(command) }
      }

    }

    @Nested
    @DisplayName("존재하지 않는 브랜드 ID가 주어지면")
    inner class ContextWith_nonexist_brand_id {

      @Test
      @DisplayName("BrandNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        val command = ProductInfoCorrectCommand(
          "0JS9VDTS492Z8", // A 브랜드 제품
          "NONEXIST",
          "0JS97BHFR9353", // '상의' 카테고리
          "수정된 상의",
          10000,
          "test"
        )
        assertThrows<BrandNotFoundException> { subject(command) }
      }
    }

    @Nested
    @DisplayName("존재하지 않는 카테고리 ID가 주어지면")
    inner class ContextWith_nonexist_category_id {

      @Test
      @DisplayName("CategoryNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        val command = ProductInfoCorrectCommand(
          "0JS9VDTS492Z8", // A 브랜드 제품
          "0JS975W4891XQ", // 'A' 브랜드
          "NONEXIST",
          "수정된 상의",
          10000,
          "test"
        )

        assertThrows<CategoryNotFoundException> { subject(command) }
      }
    }

    @Nested
    @DisplayName("모두 양호한 데이터가 주어지면")
    inner class ContextWith_valid_data {
      @Test
      @DisplayName("던져지는 예외 없이 제품 정보 등록을 진행한 후, 그 결과를 돌려준다")
      fun it_runs_successfully() {
        val command = ProductInfoCorrectCommand(
          "0JS9VDTS492Z8", // A 브랜드 제품
          "0JS975W4891XQ", // 'A' 브랜드
          "0JS97BHFR9353", // '상의' 카테고리
          "수정된 상의", // 제품이름
          10000,
          "test"
        )

        val result = assertDoesNotThrow { subject(command) }

        assertTrue(result.productId.isNotEmpty())
        assertTrue(result.productName.isNotEmpty())
        assertTrue(result.brandId.isNotEmpty())
        assertTrue(result.brandName.isNotEmpty())
        assertTrue(result.categoryId.isNotEmpty())
        assertTrue(result.categoryName.isNotEmpty())

        assertEquals(command.productName, result.productName)
        assertEquals(command.brandId, result.brandId)
        assertEquals(command.categoryId, result.categoryId)
        assertEquals(command.productPrice, result.productPrice)
      }
    }
  }
}
