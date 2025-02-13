package com.musinsa.homework.components.brand

import com.linecorp.kotlinjdsl.support.spring.data.jpa.autoconfigure.KotlinJdslAutoConfiguration
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.Import
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@DataJpaTest
@Import(KotlinJdslAutoConfiguration::class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("BrandInformant 클래스의")
class BrandInformantTest {

  @Autowired
  lateinit var brandRepository: BrandRepository

  lateinit var brandInformant: BrandInformant

  @BeforeEach
  fun init() {
    brandInformant = BrandInformant(brandRepository)
  }

  @Nested
  @DisplayName("getList(PageRequest) 메소드는")
  inner class DescribeOf_getList_method {
    fun subject(pageRequest: PageRequest) = brandInformant.getList(pageRequest)

    @Nested
    @DisplayName("caller가 이 메소드를 호출하면")
    inner class ContextWhen_call_by_caller {

      @Test
      @DisplayName("페이징 처리된 브랜드 정보 리스트를 돌려준다")
      fun it_returns_list() {
        val pagedList = assertDoesNotThrow { subject(PageRequest.of(0, 10)) }

        assertTrue { pagedList.totalElements > 0 }
        assertTrue { pagedList.totalPages > 0 }
        assertTrue { pagedList.toList().isNotEmpty() }
      }

    }
  }

  @Nested
  @DisplayName("getBrandInfo(String) 메소드는")
  inner class DescribeOf_getBrandInfo_method {
    fun subject(brandId: String) = brandInformant.getBrandInfo(brandId)

    @Nested
    @DisplayName("존재하지 않는 브랜드 ID가 주어지면")
    inner class ContextWith_nonexist_brand_id {

      @Test
      @DisplayName("BrandNotFoundException 예외를 던진다")
      fun it_throws_exceptions() {
        assertThrows<BrandNotFoundException> { subject("NONEXIST") }
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID가 주어지면")
    inner class ContextWith_exist_brand_id {

      @Test
      @DisplayName("아무런 예외를 던지지 않고, 브랜드 정보를 조회하여 돌려준다.")
      fun it_returns_brand_info() {

        // 브랜드 'A' 에 대한 조회요청
        val result = assertDoesNotThrow { subject("0JS975W4891XQ") }

        assertTrue { result.id.isNotEmpty() }
        assertTrue { result.name.isNotEmpty() }
        assertEquals("0JS975W4891XQ", result.id)
        assertEquals("A", result.name)
      }
    }
  }
}
