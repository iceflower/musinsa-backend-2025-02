package com.musinsa.homework.api.product

import com.musinsa.homework.api.product.request.ProductInfoCorrectRequest
import com.musinsa.homework.api.product.request.ProductInfoRegisterRequest
import com.musinsa.homework.api.product.request.ProductInfoRemoveRequest
import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.ProductInfoCorrector
import com.musinsa.homework.components.product.ProductInfoRegistrar
import com.musinsa.homework.components.product.ProductInfoRemover
import com.musinsa.homework.components.product.ProductInformant
import com.musinsa.homework.components.product.command.ProductInfoCorrectCommand
import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.components.product.command.ProductInfoRegisterCommand
import com.musinsa.homework.components.product.command.ProductInfoRemoveCommand
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.validation.BindException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/product")
class ProductController(
  private val productInformant: ProductInformant,
  private val productInfoRegistrar: ProductInfoRegistrar,
  private val productInfoCorrector: ProductInfoCorrector,
  private val productInfoRemover: ProductInfoRemover,
) {

  /**
   * 제품 정보 리스트를 조회하여 돌려줍니다.
   *
   * @param brandId 브랜드ID
   * @param categoryId 카테고리ID
   * @param priceFrom 제품가격 조건 from
   * @param priceTo 제품가격 조건 to
   * @param pageNumber 0번부터 시작하는 페이지 번호 (기본값 : 0)
   * @param pageSize 페이지 사이즈 (기본값: 10)
   * @return 페이징 처리한 제품 정보 리스트
   */
  @GetMapping("/list")
  fun getProductInfoList(
    @RequestParam(name = "brandId", required = false) brandId: String?,
    @RequestParam(name = "categoryId", required = false) categoryId: String?,
    @RequestParam(name = "priceFrom", required = false) priceFrom: Long?,
    @RequestParam(name = "priceTo", required = false) priceTo: Long?,
    @RequestParam(name = "pageNumber", required = false, defaultValue = "0") pageNumber: Int,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") pageSize: Int,
  ): Page<ProductInfo> {

    return productInformant.getList(
      ProductInfoListQueryCommand(
        brandId,
        categoryId,
        priceFrom,
        priceTo,
      ),
      PageRequest.of(pageNumber, pageSize)
    )
  }

  /**
   * 개별 제품 정보를 조회하여 돌려줍니다.
   *
   * @param productId 제품정보ID
   * @return 제품 정보
   * @throws ProductNotFoundException 제품 정보가 존재하지 않을 경우
   */
  @GetMapping("/{productId}")
  fun getProductInfo(@PathVariable("productId") productId: String): ProductInfo {

    return productInformant.getProductInfo(productId)
  }

  /**
   * 개별 제품정보를 수정한 후, 그 결과를 돌려줍니다.
   *
   * @param productId 수정대상 제품ID
   * @param request 수정대상 제품에 대한 수정 요청 객체
   * @param bindingResult 요청 객체의 데이터 포맷 유효성 검사 결과
   * @return 수정 완료한 제품의 정보
   *
   * @throws BindException 요청 객체의 데이터 포맷이 유효하지 않을 경우
   * @throws com.musinsa.homework.components.exception.ProductNotFoundException 수정 대상 제품이 존재하지 않을 경우
   * @throws com.musinsa.homework.components.exception.BrandNotFoundException 브랜드 정보가 존재하지 않을 경우
   * @throws com.musinsa.homework.components.exception.CategoryNotFoundException 카테고리 정보가 존재하지 않을 경우
   */
  @PatchMapping("/{productId}")
  fun correctProductInfo(
    @PathVariable("productId") productId: String,
    @RequestBody @Valid request: ProductInfoCorrectRequest,
    bindingResult: BindingResult
  ): ProductInfo {

    if (bindingResult.hasErrors()) {
      throw BindException(bindingResult)
    }

    return productInfoCorrector.correct(
      ProductInfoCorrectCommand(
        productId,
        request.brandId!!,
        request.categoryId!!,
        request.productName!!,
        request.productPrice!!,
        request.requester!!
      )
    )
  }

  /**
   * 제품 정보를 신규 등록한 후 그 결과를 돌려줍니다.
   *
   * @param request 제품 신규등록 요청 객체
   * @param bindingResult 요청 객체의 데이터 포맷 유효성 검사 결과
   *
   * @throws BindException 요청 객체의 데이터 포맷이 유효하지 않을 경우
   * @throws com.musinsa.homework.components.exception.BrandNotFoundException 브랜드 정보가 존재하지 않을 경우
   * @throws com.musinsa.homework.components.exception.CategoryNotFoundException 카테고리 정보가 존재하지 않을 경우
   */
  @PostMapping("/")
  fun registerProductInfo(
    @RequestBody @Valid request: ProductInfoRegisterRequest,
    bindingResult: BindingResult
  ): ProductInfo {

    if (bindingResult.hasErrors()) {
      throw BindException(bindingResult)
    }

    return productInfoRegistrar.register(
      ProductInfoRegisterCommand(
        request.brandId!!,
        request.categoryId!!,
        request.productName!!,
        request.productPrice!!,
        request.requester!!
      )
    )
  }

  /**
   * 제품 정보를 삭제합니다.
   *
   * @param productId 삭제대상 제품ID
   * @param request 수정대상 제품에 대한 수정 요청 객체
   * @param bindingResult 요청 객체의 데이터 포맷 유효성 검사 결과
   * @return 수정 완료한 제품의 정보
   *
   * @throws BindException 요청 객체의 데이터 포맷이 유효하지 않을 경우
   * @throws com.musinsa.homework.components.exception.ProductNotFoundException 수정 대상 제품이 존재하지 않을 경우
   */
  @DeleteMapping("/{productId}")
  fun removeProductInfo(
    @PathVariable("productId") productId: String,
    @RequestBody @Valid request: ProductInfoRemoveRequest,
    bindingResult: BindingResult
  ) {
    if (bindingResult.hasErrors()) {
      throw BindException(bindingResult)
    }
    return productInfoRemover.remove(
      ProductInfoRemoveCommand(
        productId,
        request.requester!!
      )
    )
  }
}
