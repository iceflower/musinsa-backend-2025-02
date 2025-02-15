package com.musinsa.homework.api.brand

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.homework.api.brand.request.BrandInfoCorrectRequest
import com.musinsa.homework.api.brand.request.BrandInfoRegisterRequest
import com.musinsa.homework.api.brand.request.BrandInfoRemoveRequest
import com.musinsa.homework.components.brand.BrandInfoCorrector
import com.musinsa.homework.components.brand.BrandInfoRegistrar
import com.musinsa.homework.components.brand.command.BrandInfoCorrectCommand
import com.musinsa.homework.components.brand.command.BrandInfoRegisterCommand
import com.musinsa.homework.testUtil.ApiEndpointTest
import kotlin.test.Test
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@ApiEndpointTest
@DisplayName("브랜드 정보 관련 API (/v1/api/brand) 증")
class BrandControllerTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @Autowired
  lateinit var brandInfoCorrector: BrandInfoCorrector

  @Autowired
  lateinit var brandInfoRegistrar: BrandInfoRegistrar

  @Autowired
  lateinit var jdbcTemplate: JdbcTemplate

  val objectMapper = ObjectMapper()

  @Nested
  @DisplayName("[GET] /list API는")
  inner class DescribeOf_getBrandList_method {

    fun subject(pageNumber: Int?, pageSize: Int?): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/brand/list")
        .contentType(MediaType.APPLICATION_JSON)

      if (pageNumber != null) {
        requestBuilder.param("pageNumber", pageNumber.toString())
      }

      if (pageSize != null) {
        requestBuilder.param("pageSize", pageSize.toString())
      }

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("페이징 정보가 주어지면")
    inner class ContextWith_pageInfo {

      @CsvSource(
        delimiter = ':',
        value = [
          ":", "0:", ":10", "0:10"
        ],
      )
      @ParameterizedTest
      @DisplayName("브랜드 리스트 정보를 조회하여 그 결과를 200 코드와 함께 돌려준다.")
      fun it_returns_page_info(pageNumber: Int?, pageSize: Int?) {

        subject(pageNumber, pageSize)
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].id").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].name").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").isString)

      }
    }
  }

  @Nested
  @DisplayName("[GET] /{brandId} API는")
  inner class DescribeOf_getBrandInfo_method {

    fun subject(brandId: String): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/brand/{brandId}", brandId)
        .contentType(MediaType.APPLICATION_JSON)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 브랜드ID가 주어지면")
    inner class ContextWith_nonexist_brandId {

      @Test
      @DisplayName("422 오류를 돌려준다")
      fun it_returns_422_code() {
        subject("nonexist")
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("브랜드 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드ID가 주어지면")
    inner class ContextWith_exist_brandId {

      @Test
      @DisplayName("200 코드와 함께 브랜드 정보를 돌려준다")
      fun it_returns_200_code() {
        subject("0JS975W4891XQ")
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("0JS975W4891XQ"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("A"))
      }
    }
  }

  @Nested
  @DisplayName("[PATCH] /{brandId} API는")
  inner class DescribeOf_correctBrandInfo_method {
    fun subject(brandId: String, request: BrandInfoCorrectRequest): ResultActions {

      val jsonStr = objectMapper.writer()
        .writeValueAsString(request)

      val requestBuilder = MockMvcRequestBuilders.patch("/v1/api/brand/{brandId}", brandId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonStr)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 브랜드 ID에 대한 수정 요청이 주어지면")
    inner class ContextWith_non_exist_brandId {

      @CsvSource(
        delimiter = ':',
        value = [
          "NONEXIST:새로운브랜드:test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(brandId: String, brandName: String, requester: String) {
        subject(brandId, BrandInfoCorrectRequest(brandName, requester))
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("브랜드 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID에 대한, 데이터 포맷이 유효하지 않은 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_brandId_but_invalid_request_body {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS975W4891XQ::",
          "0JS975W4891XQ:X:",
          "0JS975W4891XQ::test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("400 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_400(brandId: String, brandName: String?, requester: String?) {
        subject(brandId, BrandInfoCorrectRequest(brandName, requester))
          .andExpect(MockMvcResultMatchers.status().isBadRequest)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_REQUEST_DATA_FORMAT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("요청 데이터 포맷이 유효하지 않습니다."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].value").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].message").isString)
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID에 대한, 데이터 포맷이 유효한 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_brandId_and_valid_request_body {

      @AfterEach
      fun afterEach() {
        brandInfoCorrector.correct(
          BrandInfoCorrectCommand(
            "0JS975W4891XQ",
            "A",
            "test@test.com",
          )
        )
      }

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS975W4891XQ:X:SYSTEM",
          "0JS975W4891XQ:X:test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("200 코드와 함께 수정한 브랜드 정보를 돌려준다")
      fun it_returns_200(brandId: String, brandName: String?, requester: String?) {
        subject(brandId, BrandInfoCorrectRequest(brandName, requester))
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("0JS975W4891XQ"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("X"))
      }
    }
  }

  @Nested
  @DisplayName("[DELETE] /{brandId} API는")
  inner class DescribeOf_removeBrandInfo_method {
    fun subject(brandId: String, request: BrandInfoRemoveRequest): ResultActions {

      val jsonStr = objectMapper.writer()
        .writeValueAsString(request)

      val requestBuilder = MockMvcRequestBuilders.delete("/v1/api/brand/{brandId}", brandId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonStr)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 브랜드 ID에 대한 삭제 요청이 주어지면")
    inner class ContextWith_non_exist_brandId {

      @CsvSource(
        delimiter = ':',
        value = [
          "NONEXIST:test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(brandId: String, requester: String) {
        subject(brandId, BrandInfoRemoveRequest(requester))
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("브랜드 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID에 대한, 유효하지 않은 요청 본문 데이터가 삭제 요청으로 주어지면")
    inner class ContextWith_exist_brandId_but_invalid_request_body {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS975W4891XQ:",
        ],
      )
      @ParameterizedTest
      @DisplayName("400 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_400(brandId: String, requester: String?) {
        subject(brandId, BrandInfoRemoveRequest(requester))
          .andExpect(MockMvcResultMatchers.status().isBadRequest)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_REQUEST_DATA_FORMAT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("요청 데이터 포맷이 유효하지 않습니다."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].value").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].message").isString)
      }
    }

    @Nested
    @DisplayName("유효한 삭제요청이 주어졌지만, 삭제 요청받은 브랜드가 제품 정보를 가지고 있으면")
    inner class ContextWith_valid_request_but_it_has_product {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS975W4891XQ:test@test.com",
          "0JS975W4891XQ:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_400(brandId: String, requester: String?) {
        subject(brandId, BrandInfoRemoveRequest(requester))
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_INFO_HAS_PRODUCTS"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("삭제하려는 브랜드의 상품이 존재합니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID에 대한, 유효한 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_brandId_and_valid_request_body {

      fun givenBrandId(): String {
        return brandInfoRegistrar.register(
          BrandInfoRegisterCommand(
            "새로생성한브랜드",
            "test@test.com",
          )
        ).id
      }

      @AfterEach
      fun afterEach() {
        jdbcTemplate.execute("delete from brand where brand_name =  '새로생성한브랜드'")
      }

      @ValueSource(
        strings = [
          "SYSTEM",
          "test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("200 코드와 함께 수정한 브랜드 정보를 돌려준다")
      fun it_returns_200(requester: String) {
        subject(givenBrandId(), BrandInfoRemoveRequest(requester))
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
      }
    }
  }


  @Nested
  @DisplayName("[POST] / API는")
  inner class DescribeOf_registerBrandInfo_method {
    fun subject(request: BrandInfoRegisterRequest): ResultActions {

      val jsonStr = objectMapper.writer()
        .writeValueAsString(request)

      val requestBuilder = MockMvcRequestBuilders.post("/v1/api/brand/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonStr)

      return mockMvc.perform(requestBuilder)
    }


    @Nested
    @DisplayName("데이터 포맷이 유효하지 않은 요청 본문 데이터가 신규 둥록 요청으로 주어지면")
    inner class ContextWith_exist_brandId_but_invalid_request_body {

      @CsvSource(
        delimiter = ':',
        value = [
          ":",
          "X:",
          ":test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("400 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_400(brandName: String?, requester: String?) {
        subject(BrandInfoRegisterRequest(brandName, requester))
          .andExpect(MockMvcResultMatchers.status().isBadRequest)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("INVALID_REQUEST_DATA_FORMAT"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("요청 데이터 포맷이 유효하지 않습니다."))
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].value").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[*].message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.detailed[0].message").isString)
      }
    }

    @Nested
    @DisplayName("존재하는 브랜드 ID에 대한, 데이터 포맷이 유효한 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_brandId_and_valid_request_body {

      @AfterEach
      fun afterEach() {
        jdbcTemplate.execute("delete from brand where brand_name =  'X'")
      }

      @CsvSource(
        delimiter = ':',
        value = [
          "X:SYSTEM",
          "X:test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("200 코드와 함께 수정한 브랜드 정보를 돌려준다")
      fun it_returns_200(brandName: String, requester: String) {
        subject(BrandInfoRegisterRequest(brandName, requester))
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("X"))
      }
    }
  }
}
