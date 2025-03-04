package com.musinsa.homework.components.brand

import com.musinsa.homework.components.brand.command.BrandInfoRegisterCommand
import com.musinsa.homework.components.exception.AlreadyUsedBrandNameException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@ComponentUsingDataJpaTest
@DisplayName("BrandInfoRegistrar 클래스의")
class BrandInfoRegistrarTest {

  @Autowired
  lateinit var brandRepository: BrandRepository

  lateinit var brandInfoRegistrar: BrandInfoRegistrar

  @BeforeEach
  fun init() {
    brandInfoRegistrar = BrandInfoRegistrar(brandRepository)
  }

  @Nested
  @DisplayName("register(BrandInfoRegisterCommand) 메소드는")
  inner class DescribeOf_register_method {

    fun subject(command: BrandInfoRegisterCommand) = brandInfoRegistrar.register(command)

    @Nested
    @DisplayName("이미 존재하는 브랜드 이름이 주어지면")
    inner class ContextWith_already_exist_brandName {

      @Test
      @DisplayName("AlreadyUsedBrandNameException 예외를 던진다.")
      fun it_throws_exception() {
        val exception = assertThrows<AlreadyUsedBrandNameException> { subject(BrandInfoRegisterCommand("A", "test")) }
        assertEquals("이미 사용중인 브랜드명입니다.", exception.message!!)
      }

    }

    @Nested
    @DisplayName("존재하지 않는 브랜드 이름이 주어지면")
    inner class ContextWith_non_exist_brandName {

      @Test
      @DisplayName("아무런 예외를 던지지 않고 브랜드 정보를 생성한 후, 그 결과를 돌려준다.")
      fun it_throws_exception() {
        val result = assertDoesNotThrow { subject(BrandInfoRegisterCommand("Z", "test")) }
        assertTrue(result.id.isNotEmpty())
        assertTrue(result.name.isNotEmpty())
        assertEquals("Z", result.name)
      }
    }
  }
}
