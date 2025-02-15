package com.musinsa.homework.api.product

import com.fasterxml.jackson.databind.ObjectMapper
import com.musinsa.homework.api.product.request.ProductInfoCorrectRequest
import com.musinsa.homework.api.product.request.ProductInfoRegisterRequest
import com.musinsa.homework.api.product.request.ProductInfoRemoveRequest
import com.musinsa.homework.components.product.ProductInfoCorrector
import com.musinsa.homework.components.product.ProductInfoRegistrar
import com.musinsa.homework.components.product.command.ProductInfoCorrectCommand
import com.musinsa.homework.components.product.command.ProductInfoRegisterCommand
import com.musinsa.homework.testUtil.ApiEndpointTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
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
@DisplayName("제품 정보 관련 API (/v1/api/product) 증")
class ProductControllerTest {

  @Autowired
  lateinit var mockMvc: MockMvc

  @Autowired
  lateinit var jdbcTemplate: JdbcTemplate

  @Autowired
  lateinit var productInfoRegistrar: ProductInfoRegistrar

  @Autowired
  lateinit var productInfoCorrector: ProductInfoCorrector

  val objectMapper = ObjectMapper()

  @Nested
  @DisplayName("[GET] /list API는")
  inner class DescribeOf_getProductInfoList_method {

    fun subject(brandId: String?, categoryId: String?, priceFrom: Long?, priceTo: Long?, pageNumber: Int?, pageSize: Int?): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/product/list")
        .contentType(MediaType.APPLICATION_JSON)

      if (brandId != null) {
        requestBuilder.param("brandId", brandId)
      }

      if (categoryId != null) {
        requestBuilder.param("categoryId", categoryId)
      }

      if (priceFrom != null) {
        requestBuilder.param("priceFrom", priceFrom.toString())
      }

      if (priceTo != null) {
        requestBuilder.param("priceTo", priceTo.toString())
      }

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
          ":::::",
          "0JS975W4891XQ:::::",
          "0JS975W4891XQ:0JS97BHFR9353::::",
          "0JS975W4891XQ::1000:::",
          "0JS975W4891XQ:::20000::",
          "0JS975W4891XQ::::0:",
          "0JS975W4891XQ:::::10",
          "0JS975W4891XQ::1000:20000:0:10",
          "0JS975W4891XQ:0JS97BHFR9353::20000:0:10",
          "0JS975W4891XQ:0JS97BHFR9353:1000::0:10",
          "0JS975W4891XQ:0JS97BHFR9353:1000:20000::10",
          "0JS975W4891XQ:0JS97BHFR9353:1000:20000:0:",
          "0JS975W4891XQ:0JS97BHFR9353:1000:20000:0:10",
          ":0JS97BHFR9353::::",
          ":0JS97BHFR9353:1000:::",
          ":0JS97BHFR9353::20000::",
          ":0JS97BHFR9353:::0:",
          ":0JS97BHFR9353::::10",
          ":0JS97BHFR9353::20000:0:10",
          ":0JS97BHFR9353:1000::0:10",
          ":0JS97BHFR9353:1000:20000::10",
          ":0JS97BHFR9353:1000:20000:0:",
          ":0JS97BHFR9353:1000:20000:0:10",
          "::1000:::",
          "::1000:20000::",
          "::1000::0:",
          "::1000:::10",
          "::1000::0:10",
          "::1000:20000::10",
          "::1000:20000:0:",
          "::1000:20000:0:10",
          ":::20000::",
          ":::20000:0:",
          ":::20000::10",
          ":::20000:0:10",
          "::::0:",
          "::::0:10",
          ":::::10",
        ],
      )
      @ParameterizedTest
      @DisplayName("제품 정보 리스트를 조회하여 그 결과를 200 코드와 함께 돌려준다.")
      fun it_returns_page_info(brandId: String?, categoryId: String?, priceFrom: Long?, priceTo: Long?, pageNumber: Int?, pageSize: Int?) {

        subject(brandId, categoryId, priceFrom, priceTo, pageNumber, pageSize)
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.content").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].productId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].productId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].productId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].productName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].productName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].productName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].categoryId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].categoryId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].categoryId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].categoryName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].brandId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].brandId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].brandId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[*].brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].brandName").isString)
      }
    }
  }

  @Nested
  @DisplayName("[GET] /{productId} API는")
  inner class DescribeOf_getProductInfo_method {

    fun subject(productId: String): ResultActions {

      val requestBuilder = MockMvcRequestBuilders.get("/v1/api/product/{productId}", productId)
        .contentType(MediaType.APPLICATION_JSON)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 제품ID가 주어지면")
    inner class ContextWith_nonexist_productId {

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
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("PRODUCT_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("제품 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 제품ID가 주어지면")
    inner class ContextWith_exist_producctId {

      @Test
      @DisplayName("200 코드와 함께 제품 정보를 돌려준다")
      fun it_returns_200_code() {
        subject("0JS9VDTS492Z8")
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value("0JS9VDTS492Z8"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value("[A] 상의"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value("0JS97BHFR9353"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("상의"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value("0JS975W4891XQ"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").value("A"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").isNumber)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(11200))
      }
    }
  }

  @Nested
  @DisplayName("[POST] /{productId} API는")
  inner class DescribeOf_registerProductInfo_method {
    fun subject(request: ProductInfoRegisterRequest): ResultActions {

      val jsonStr = objectMapper.writer()
        .writeValueAsString(request)

      val requestBuilder = MockMvcRequestBuilders.post("/v1/api/product/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonStr)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 브랜드ID가 포함된 잘못된 신규등록 요청이 주어지면")
    inner class ContextWith_exist_brand_id_is_nonexist {

      @CsvSource(
        delimiter = ':',
        value = [
          "NONEXIST:0JS97BHFR9353:신규등록할제품명:1000:test@test.com",
          "NONEXIST:0JS97BHFR9353:신규등록할제품명:1000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(brandId: String, categoryId: String, productName: String, productPrice: Long, requester: String) {
        subject(ProductInfoRegisterRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
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
    @DisplayName("존재하지 않는 카테고리ID가 포함된 잘못된 신규등록 요청이 주어지면")
    inner class ContextWith_category_id_is_nonexist {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS975W4891XQ:NONEXIST:신규등록할제품명:1000:test@test.com",
          "0JS975W4891XQ:NONEXIST:신규등록할제품명:1000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(brandId: String, categoryId: String, productName: String, productPrice: Long, requester: String) {
        subject(ProductInfoRegisterRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
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
    @DisplayName("데이터 포멧이 유효하지 않은 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_productId_but_invalid_request_body {

      @CsvSource(
        delimiter = ':',
        value = [
          "::::",
          ":0JS97BHFR9353:신규등록할제품명:1000:test@test.com",
          "0JS975W4891XQ::신규등록할제품명:1000:test@test.com",
          "0JS975W4891XQ:0JS97BHFR9353::1000:test@test.com",
          "0JS975W4891XQ:0JS97BHFR9353:신규등록할제품명::test@test.com",
          "0JS975W4891XQ:0JS97BHFR9353:신규등록할제품명:1000:",
        ],
      )
      @ParameterizedTest
      @DisplayName("400 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_400(brandId: String?, categoryId: String?, productName: String?, productPrice: Long?, requester: String?) {
        subject(ProductInfoRegisterRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isBadRequest)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
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
    @DisplayName("데이터 포맷이 유효한 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_valid_request_body {

      @AfterEach
      fun afterEach() {
        jdbcTemplate.execute("delete from product where product_name = '신규등록할제품명'")
      }

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS976HPR93QN:0JS97CN409252:신규등록할제품명:9000:test@test.com",
          "0JS976HPR93QN:0JS97CN409252:신규등록할제품명:9000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("200 코드와 함께 변경된 제품 정보를 돌려준다.")
      fun it_returns_200(brandId: String, categoryId: String, productName: String, productPrice: Long, requester: String) {

        subject(ProductInfoRegisterRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(productName))
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value(categoryId))
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("바지"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(brandId))
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").value("B"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").isNumber)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(productPrice))
      }
    }
  }

  @Nested
  @DisplayName("[PATCH] /{productId} API는")
  inner class DescribeOf_correctProductInfo_method {
    fun subject(productId: String, request: ProductInfoCorrectRequest): ResultActions {

      val jsonStr = objectMapper.writer()
        .writeValueAsString(request)

      val requestBuilder = MockMvcRequestBuilders.patch("/v1/api/product/{productId}", productId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonStr)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 제품 ID에 대한 수정 요청이 주어지면")
    inner class ContextWith_non_exist_productId {

      @CsvSource(
        delimiter = ':',
        value = [
          "NONEXIST:0JS975W4891XQ:0JS97BHFR9353:변경할제품명:1000:test@test.com",
          "NONEXIST:0JS975W4891XQ:0JS97BHFR9353:변경할제품명:1000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(
        productId: String,
        brandId: String,
        categoryId: String,
        productName: String,
        productPrice: Long,
        requester: String
      ) {
        subject(productId, ProductInfoCorrectRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("PRODUCT_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("제품 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 제품 ID에 대한, 존재하지 않는 브랜드ID가 포함된 잘못된 수정요청이 주어지면")
    inner class ContextWith_exist_productId_but_brand_id_is_nonexist {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS9VDTS492Z8:NONEXIST:0JS97BHFR9353:변경할제품명:1000:test@test.com",
          "0JS9VDTS492Z8:NONEXIST:0JS97BHFR9353:변경할제품명:1000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(
        productId: String,
        brandId: String,
        categoryId: String,
        productName: String,
        productPrice: Long,
        requester: String
      ) {
        subject(productId, ProductInfoCorrectRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
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
    @DisplayName("존재하는 제품 ID에 대한, 존재하지 않는 카테고리ID가 포함된 잘못된 수정요청이 주어지면")
    inner class ContextWith_exist_productId_but_category_id_is_nonexist {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS9VDTS492Z8:0JS975W4891XQ:NONEXIST:변경할제품명:1000:test@test.com",
          "0JS9VDTS492Z8:0JS975W4891XQ:NONEXIST:변경할제품명:1000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(
        productId: String,
        brandId: String,
        categoryId: String,
        productName: String,
        productPrice: Long,
        requester: String
      ) {
        subject(productId, ProductInfoCorrectRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
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
    @DisplayName("존재하는 제품 ID에 대한, 데이터 포맷이 유효하지 않은 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_productId_but_invalid_request_body {

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS9VDTS492Z8:::::",
          "0JS9VDTS492Z8::0JS97BHFR9353:변경할제품명:1000:test@test.com",
          "0JS9VDTS492Z8:0JS975W4891XQ::변경할제품명:1000:test@test.com",
          "0JS9VDTS492Z8:0JS975W4891XQ:0JS97BHFR9353::1000:test@test.com",
          "0JS9VDTS492Z8:0JS975W4891XQ:0JS97BHFR9353:변경할제품명::test@test.com",
          "0JS9VDTS492Z8:0JS975W4891XQ:0JS97BHFR9353:변경할제품명:1000:",
        ],
      )
      @ParameterizedTest
      @DisplayName("400 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_400(
        productId: String,
        brandId: String?,
        categoryId: String?,
        productName: String?,
        productPrice: Long?,
        requester: String?
      ) {
        subject(productId, ProductInfoCorrectRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isBadRequest)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
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
    @DisplayName("존재하는 제품 ID에 대한, 데이터 포맷이 유효한 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_productId_and_valid_request_body {

      @AfterEach
      fun afterEach() {
        productInfoCorrector.correct(
          ProductInfoCorrectCommand(
            "0JS9VDTS492Z8", // 제품ID
            "0JS975W4891XQ", // 브랜드ID
            "0JS97BHFR9353", // 카테고리ID
            "[A] 상의", // 제품명
            11200, // 제품가격
            "SYSTEM"
          )
        )
      }

      @CsvSource(
        delimiter = ':',
        value = [
          "0JS9VDTS492Z8:0JS976HPR93QN:0JS97CN409252:변경한제품이름:9000:test@test.com",
          "0JS9VDTS492Z8:0JS976HPR93QN:0JS97CN409252:변경한제품이름:9000:SYSTEM",
        ],
      )
      @ParameterizedTest
      @DisplayName("200 코드와 함께 변경된 제품 정보를 돌려준다.")
      fun it_returns_200(
        productId: String,
        brandId: String,
        categoryId: String,
        productName: String,
        productPrice: Long,
        requester: String
      ) {

        subject(productId, ProductInfoCorrectRequest(brandId, categoryId, productName, productPrice, requester))
          .andDo(MockMvcResultHandlers.print())
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(productId))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(productName))
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryId").value(categoryId))
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.categoryName").value("바지"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandId").value(brandId))
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").isString)
          .andExpect(MockMvcResultMatchers.jsonPath("$.brandName").value("B"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").isNumber)
          .andExpect(MockMvcResultMatchers.jsonPath("$.productPrice").value(productPrice))
      }
    }
  }

  @Nested
  @DisplayName("[DELETE] /{productId} API는")
  inner class DescribeOf_removeProductInfo_method {
    fun subject(productId: String, request: ProductInfoRemoveRequest): ResultActions {

      val jsonStr = objectMapper.writer()
        .writeValueAsString(request)

      val requestBuilder = MockMvcRequestBuilders.delete("/v1/api/product/{productId}", productId)
        .contentType(MediaType.APPLICATION_JSON)
        .content(jsonStr)

      return mockMvc.perform(requestBuilder)
    }

    @Nested
    @DisplayName("존재하지 않는 제품 ID에 대한 삭제 요청이 주어지면")
    inner class ContextWith_non_exist_productId {

      @CsvSource(
        delimiter = ':',
        value = [
          "NONEXIST:test@test.com",
        ],
      )
      @ParameterizedTest
      @DisplayName("422 코드와 함께 오류 메시지를 돌려준다.")
      fun it_returns_422(brandId: String, requester: String) {
        subject(brandId, ProductInfoRemoveRequest(requester))
          .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity)
          .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.code").isString)
//          .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("BRAND_INFO_NOT_FOUND"))
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isNotEmpty)
          .andExpect(MockMvcResultMatchers.jsonPath("$.message").isString)
//          .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("브랜드 정보를 찾을 수 없습니다."))
      }
    }

    @Nested
    @DisplayName("존재하는 제품 ID에 대한, 유효한 요청 본문 데이터가 수정 요청으로 주어지면")
    inner class ContextWith_exist_productId_and_valid_request_body {

      fun givenProductId(): String {
        return productInfoRegistrar.register(
          ProductInfoRegisterCommand(
            "0JS975W4891XQ", // A 브랜드
            "0JS97BHFR9353", // 상의 카테고리
            "새로 생성한 제품", // 제품명
            5000,
            "test@test.com",
          )
        ).productId
      }

      @AfterEach
      fun afterEach() {
        jdbcTemplate.execute("delete from product where product_name =  '새로 생성한 제품'")
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
        subject(givenProductId(), ProductInfoRemoveRequest(requester))
          .andExpect(MockMvcResultMatchers.status().isOk)
          .andDo(MockMvcResultHandlers.print())
      }
    }
  }
}
