package com.musinsa.homework.components.product

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.jpa.entities.product.ProductRepository
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource


@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@DataJpaTest
@Import(KotlinJdslAutoConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("ProductInfoRemover 클래스의")
class ProductInformantTest {

  @Autowired
  lateinit var productRepository: ProductRepository

  lateinit var productInformant: ProductInformant

  @BeforeEach
  fun init() {
    productInformant = ProductInformant(productRepository)
  }

  @Nested
  @DisplayName("getList(ProductInfoListQueryCommand, PageRequest) 메소드는")
  inner class DescribeOf_getList_method {
    fun subject(command: ProductInfoListQueryCommand, pageRequest: PageRequest) =
      productInformant.getList(command, pageRequest)


    @Nested
    @DisplayName("caller에 의해 메소드가 호출되면")
    inner class ContextWith_call_by_caller {

      @ParameterizedTest
      @CsvSource(
        delimiter = ':',
        value = [
          ":::",
          "0JS975W4891XQ:::",
          "0JS975W4891XQ:0JS97BHFR9353::",
          "0JS975W4891XQ:0JS97BHFR9353:1000:",
          "0JS975W4891XQ:0JS97BHFR9353:1000:100000",
          ":0JS97BHFR9353:1000:100000",
          "::1000:100000",
          ":::100000",
        ],
      )
      @DisplayName("조회조건에 맞는 데이터를 조회한 후 그 결과를 돌려준다")
      fun it_returns_list(brandId: String?, categoryId: String?, priceFrom: Long?, priceTo: Long?) {
        val command = ProductInfoListQueryCommand(
          brandId, categoryId, priceFrom, priceTo
        )

        val pageRequest = PageRequest.of(0, 10)

        val result = assertDoesNotThrow { subject(command, pageRequest) }

        assertTrue { result.totalElements > 0 }
        assertTrue { result.totalPages > 0 }
        assertFalse { result.isEmpty }
        assertTrue { result.toList().isNotEmpty() }

        result.toList().forEach {
          assertTrue { it.productId.isNotEmpty() }
          assertTrue { it.productName.isNotEmpty() }
          assertTrue { it.categoryId.isNotEmpty() }
          assertTrue { it.categoryName.isNotEmpty() }
          assertTrue { it.brandId.isNotEmpty() }
          assertTrue { it.brandName.isNotEmpty() }
        }
      }
    }
  }

  @Nested
  @DisplayName("getProductInfo(String) 메소드는")
  inner class DescribeOf_getProductInfo_method {

    fun subject(productId: String) = productInformant.getProductInfo(productId)

    @Nested
    @DisplayName("존재하지 않는 제품 ID가 주어지면")
    inner class ContextWith_nonexist_product_id {
      @Test
      @DisplayName("ProductNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        assertThrows<ProductNotFoundException> { subject("NONEXIST") }
      }
    }

    @Nested
    @DisplayName("존재하는 제품 ID가 주어지면")
    inner class ContextWith_exist_product_id {
      @Test
      @DisplayName("ProductNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        val result = assertDoesNotThrow { subject("0JS9VDTS492Z8") }

        assertTrue { result.productId.isNotEmpty() }
        assertTrue { result.productName.isNotEmpty() }
        assertTrue { result.categoryId.isNotEmpty() }
        assertTrue { result.categoryName.isNotEmpty() }
        assertTrue { result.productPrice > 0 }
      }
    }
  }
}
