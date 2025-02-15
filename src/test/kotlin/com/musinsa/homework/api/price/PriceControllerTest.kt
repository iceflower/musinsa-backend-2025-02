package com.musinsa.homework.api.price

import com.musinsa.homework.testUtil.RedisTestContainer
import kotlin.test.Test
import org.hamcrest.Matchers.greaterThan
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ActiveProfiles("test")
@TestPropertySource(locations = ["classpath:application-test.yml"])
@RedisTestContainer
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("가격 정보 관련 API (/v1/api/price) 증")
class PriceControllerTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @Nested
  @DisplayName("카테고리별 최저가 브랜드 상품 리스트 조회 API (/lowest-by-cagegories-list) 는")
  inner class DescribeOf_getLowestByCategoriesList_method {

    fun subject(): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/price/lowest-by-cagegories-list")
        .contentType(MediaType.APPLICATION_JSON)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("호출이 되어지면")
    inner class ContextWhen_called {

      @Test
      @DisplayName("200 코드와 함께 카테고리별 브랜드 상품 리스트를 돌려준다.")
      fun it_returns_200() {
        subject()
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice").isNumber)
          .andExpect(MockMvcResultMatchers.jsonPath("$.totalPrice", greaterThan(0)))
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].price").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].price").isNumber)
      }
    }
  }

  @Nested
  @DisplayName("단일 브랜드 선택시 카테고리별 최저가 브랜드 상품 리스트 조회 API (/lowest-by-cagegory-list/when-choosing-a-single-brand) 는")
  inner class DescribeOf_getLowestByCategoriesWhenChoosingASingleBrand_method {

    fun subject(): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/price/lowest-by-cagegory-list/when-choosing-a-single-brand")
        .contentType(MediaType.APPLICATION_JSON)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("호출이 되어지면")
    inner class ContextWhen_called {

      @Test
      @DisplayName("200 코드와 함께 카테고리별 브랜드 상품 리스트를 돌려준다.")
      fun it_returns_200() {
        subject()
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice").isMap)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.totalPrice").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.totalPrice").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.totalPrice").isNumber)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.totalPrice", greaterThan(0)))
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products[*].categoryName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products[*].categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products[0].categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products[*].price").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products[*].price").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPrice.products[0].price").isNumber)
      }
    }
  }

  @Nested
  @DisplayName("특정 카테고리 내 최저가/최고가 제품 조회 API (/lowest-and-highest-info/of-category/{categoryName} 는")
  inner class DecrribeOf_getLowestPriceAndHighestPriceProductsOfCategory_method {

    fun subject(categoryName: String): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/price/lowest-and-highest-info/of-category/{categoryName}", categoryName)
        .contentType(MediaType.APPLICATION_JSON)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 카테고리명이 주어지면")
    inner class ContextWith_non_exist_categoryName {

      @Test
      @DisplayName("422 코드와 함께 오류를 돌려준다.")
      fun it_returns_422() {
        subject("nonexist")
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("CATEGORY_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("카테고리 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하지 않는 카테고리명이 주어지면")
    inner class ContextWith_exist_categoryName {
      @ParameterizedTest
      @ValueSource(strings = ["스니커즈"])
      @DisplayName("200 코드와 함께, 최저가 및 최고가 정보를 돌려준다.")
      fun it_returns_200(categoryName: String) {
        subject(categoryName)
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value(categoryName))
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList[*].brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList[*].brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList[0].brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList[*].price").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList[*].price").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.lowestPriceList[0].price").isNumber)
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList[*].brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList[*].brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList[0].brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList[*].price").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList[*].price").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.highestPriceList[0].price").isNumber)
      }
    }
  }
}
