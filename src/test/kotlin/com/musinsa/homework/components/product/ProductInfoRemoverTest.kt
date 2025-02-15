package com.musinsa.homework.components.product

import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoRemoveCommand
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@ComponentUsingDataJpaTest
@DisplayName("ProductInfoRemover 클래스의")
class ProductInfoRemoverTest {

  @Autowired
  lateinit var productRepository: ProductRepository

  lateinit var productInfoRemover: ProductInfoRemover

  @BeforeEach
  fun init() {
    productInfoRemover = ProductInfoRemover(productRepository)
  }

  @Nested
  @DisplayName("remove(ProductInfoRemoveCommand) 메소드는")
  inner class DescribeOf_remove_method {

    fun subject(command: ProductInfoRemoveCommand) = productInfoRemover.remove(command)

    @Nested
    @DisplayName("존재하지 않는 제품 ID가 주어지면")
    inner class ContextWith_nonexist_product_id {

      @Test
      @DisplayName("ProductNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        val command = ProductInfoRemoveCommand(
          "NONEXIST",
          "test"
        )
        assertThrows<ProductNotFoundException> { subject(command) }
      }
    }

    @Nested
    @DisplayName("양호한 데이터가 주어지면")
    inner class ContextWith_valid_data {
      @Test
      @DisplayName("던져지는 예외 없이 제품 정보 삭제를 진행한다")
      fun it_runs_successfully() {
        val command = ProductInfoRemoveCommand(
          "0JS9VDTS492Z8", // A 브랜드 제품
          "test"
        )

        assertDoesNotThrow { subject(command) }

      }
    }

  }
}
