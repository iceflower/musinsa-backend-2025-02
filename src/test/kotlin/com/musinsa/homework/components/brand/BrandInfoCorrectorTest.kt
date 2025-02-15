package com.musinsa.homework.components.brand

import com.musinsa.homework.components.brand.command.BrandInfoCorrectCommand
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.testUtil.ComponentUsingDataJpaTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

@ComponentUsingDataJpaTest
@DisplayName("BrandInfoCorrector 클래스의")
class BrandInfoCorrectorTest {

  @Autowired
  lateinit var brandRepository: BrandRepository

  lateinit var brandInfoCorrector: BrandInfoCorrector

  @BeforeEach
  fun init() {
    brandInfoCorrector = BrandInfoCorrector(brandRepository)
  }

  @Nested
  @DisplayName("correct(BrandInfoCorrectCommand) 메소드는")
  inner class DescribeOf_correct_method {

    fun subject(command: BrandInfoCorrectCommand) = brandInfoCorrector.correct(command)

    @Nested
    @DisplayName("존재하지 않는 브랜드ID가 주어지면")
    inner class ContextWith_non_exist_brandId {

      @Test
      @DisplayName("BrandNotFoundException 예외를 던진다")
      fun it_throws_exception() {
        val exception = assertThrows<BrandNotFoundException> { subject(BrandInfoCorrectCommand("NONEXIST-ID", "Z", "test")) }
        assertEquals("브랜드 정보를 찾을 수 없습니다.", exception.message!!)
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드ID가 주어지면")
    inner class ContextWith_exist_brandId {

      @Test
      @DisplayName("아무런 예외를 던지지 않고 브랜드 정보 수정을 진행한 후, 그 결과를 돌려준다")
      fun it_runs_successfully() {
        // 'A' 브랜드에 대한 수정 요청
        val result = assertDoesNotThrow { subject(BrandInfoCorrectCommand("0JS975W4891XQ", "Z", "test")) }
        assertTrue(result.id.isNotEmpty())
        assertTrue(result.name.isNotEmpty())
        assertEquals("0JS975W4891XQ", result.id)
        assertNotEquals("A", result.name)
        assertEquals("Z", result.name)
      }
    }
  }
}
