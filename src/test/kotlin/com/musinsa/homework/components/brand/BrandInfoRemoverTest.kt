package com.musinsa.homework.components.brand

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.musinsa.homework.components.brand.command.BrandInfoRegisterCommand
import com.musinsa.homework.components.brand.command.BrandInfoRemoveCommand
import com.musinsa.homework.components.exception.BrandHasProductsException
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
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

@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@DataJpaTest
@Import(KotlinJdslAutoConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("BrandInfoRemover 클래스의")
class BrandInfoRemoverTest {

  @Autowired
  lateinit var brandRepository: BrandRepository

  @Autowired
  lateinit var productRepository: ProductRepository

  lateinit var brandInfoRemover: BrandInfoRemover

  @BeforeEach
  fun init() {
    brandInfoRemover = BrandInfoRemover(brandRepository, productRepository)
  }

  @Nested
  @DisplayName("remove(BrandInfoRemoveCommand) 메소드는")
  inner class DescribeOf_remove_method {

    fun subject(command: BrandInfoRemoveCommand) = brandInfoRemover.remove(command)

    @Nested
    @DisplayName("존재하지 않는 브랜드ID가 주어지면")
    inner class ContextWith_non_exist_brandId {

      @Test
      @DisplayName("BrandNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        assertThrows<BrandNotFoundException> { subject(BrandInfoRemoveCommand("NONEXIST-ID", "test")) }
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID지만, 이 브랜드의 제품 또한 존재하는 브랜드가 주어지면")
    inner class ContextWith_exist_brandId_but_also_has_product {
      @Test
      @DisplayName("BrandHasProductsException 예외를 던진다")
      fun it_throws_exception() {
        // 'A 브랜드에 대한 삭제요청'
        assertThrows<BrandHasProductsException> { subject(BrandInfoRemoveCommand("0JS975W4891XQ", "test")) }
      }
    }

    @Nested
    @DisplayName("존재하고, 제품 정보를 가지고 있지 않은 브랜드 ID가 주어지면")
    inner class ContextWith_exist_brandId_and_not_has_product {

      /**
       * 제품 정보를 가지고 있지 않은 브랜드 정보를 생성한 후 그 브랜드의 ID를 돌려줍니다.
       *
       * @return 제품 정보를 가지고 있지 않은 브랜드 ID
       */
      fun givenBrandId(): String {
        val brandInfoRegistrar = BrandInfoRegistrar(brandRepository)
        return brandInfoRegistrar.register(BrandInfoRegisterCommand("Y", "test")).id
      }

      @Test
      @DisplayName("아무런 예외를 던지지 않고, 삭제 작업을 진행한다")
      fun it_runs_successfully() {
        val brandId = givenBrandId()
        assertDoesNotThrow { subject(BrandInfoRemoveCommand(brandId, "test")) }
      }
    }
  }
}
