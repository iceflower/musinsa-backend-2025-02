package com.musinsa.homework.api.brand

import com.musinsa.homework.api.brand.request.BrandInfoCorrectRequest
import com.musinsa.homework.api.brand.request.BrandInfoRegisterRequest
import com.musinsa.homework.api.brand.request.BrandInfoRemoveRequest
import com.musinsa.homework.api.brand.response.BrandInfoResponse
import com.musinsa.homework.components.brand.BrandInfoCorrector
import com.musinsa.homework.components.brand.BrandInfoRegistrar
import com.musinsa.homework.components.brand.BrandInfoRemover
import com.musinsa.homework.components.brand.BrandInformant
import com.musinsa.homework.components.brand.command.BrandInfoCorrectCommand
import com.musinsa.homework.components.brand.command.BrandInfoRegisterCommand
import com.musinsa.homework.components.brand.command.BrandInfoRemoveCommand
import com.musinsa.homework.components.exception.AlreadyUsedBrandNameException
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
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
@RequestMapping("/v1/api/brand")
class BrandController(
  private val brandInformant: BrandInformant,
  private val brandInfoRegistrar: BrandInfoRegistrar,
  private val brandInfoCorrector: BrandInfoCorrector,
  private val brandInfoRemover: BrandInfoRemover
) {

  /**
   * 브랜드 리스트를 조회하여 돌려줍니다.
   *
   * @param pageNumber 0부터 시작하는 페이지 번호 (기본값: 0)
   * @param pageSize 페이지 크기 (기본값 : 10)
   * @return 브랜드 리스트
   */
  @GetMapping("/list")
  fun getList(
    @RequestParam(name = "pageNumber", required = false, defaultValue = "0") pageNumber: Int,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") pageSize: Int
  ): ResponseEntity<Page<BrandInfo>> {

    return ResponseEntity.ok(brandInformant.getList(PageRequest.of(pageNumber, pageSize)))
  }

  /**
   * 개별 브랜드 정보를 조회하여 돌려줍니다.
   *
   * @param brandId 브랜드ID
   * @return 개별 브랜드 리스트
   * @throws BrandNotFoundException 브랜드 정보를 찾을 수 없을 경우
   */
  @GetMapping("/{brandId}")
  fun getInfo(@PathVariable("brandId") brandId: String): BrandInfo {

    return brandInformant.getBrandInfo(brandId)
  }

  /**
   * 개별 브랜드 정보를 수정합니다.
   *
   * @param brandId 수정대상 브랜드ID
   * @param request 수정 요청 객체
   * @param bindingResult 수정 요청 객체의 유효성 검사 결과
   * @return 수정 완료한 브랜드 정보
   *
   * @throws BindException 수정 요청 객체의 유효성 검사 결과 오류를 발견했을 경우
   * @throws BrandNotFoundException 브랜드 정보를 찾을 수 없을 경우
   */
  @PatchMapping("/{brandId}")
  fun correct(
    @PathVariable("brandId") brandId: String,
    @RequestBody @Valid request: BrandInfoCorrectRequest,
    bindingResult: BindingResult
  ): ResponseEntity<BrandInfoResponse> {

    if (bindingResult.hasErrors()) {
      throw BindException(bindingResult)
    }

    val changedBrandInfo = brandInfoCorrector.correct(
      BrandInfoCorrectCommand(
        brandId,
        request.brandName!!,
        request.requester!!
      )
    )

    return ResponseEntity.ok(
      BrandInfoResponse(
        changedBrandInfo.id,
        changedBrandInfo.name,
      )
    )
  }

  /**
   *브랜드 정보를 신규 등록하고, 그 결과를 돌려줍니다.
   *
   * @param request 신규 등록 요청 객체
   * @param bindingResult 신규 등록 요청 객체의 유효성 검사 결과
   * @return 신규등록 완료한 브랜드 정보
   *
   * @throws BindException 신규 등록 요청 객체의 유효성 검사 결과 오류를 발견했을 경우
   * @throws AlreadyUsedBrandNameException 브랜드명이 이미 사용중일 경우
   */
  @PostMapping
  fun register(
    @RequestBody @Valid request: BrandInfoRegisterRequest,
    bindingResult: BindingResult
  ): ResponseEntity<BrandInfo> {

    if (bindingResult.hasErrors()) {
      throw BindException(bindingResult)
    }

    return ResponseEntity.ok(
      brandInfoRegistrar.register(
        BrandInfoRegisterCommand(
          request.brandName!!,
          request.requester!!
        )
      )
    )
  }

  /**
   * 개별 브랜드 정보를 삭제합니다.
   *
   * @param brandId 삭제대상 브랜드ID
   * @param request 삭제 요청 객체
   * @param bindingResult 삭제요청 객체의 유효성 검사 결과
   * @return 202 code
   *
   * @throws BindException 삭제요청 객체의 유효성 검사 결과 오류를 발견했을 경우
   * @throws BrandNotFoundException 브랜드 정보를 찾을 수 없을 경우
   */
  @DeleteMapping("/{brandId}")
  fun remove(
    @PathVariable("brandId") brandId: String,
    @RequestBody @Valid request: BrandInfoRemoveRequest,
    bindingResult: BindingResult
  ): ResponseEntity<String> {

    if (bindingResult.hasErrors()) {
      throw BindException(bindingResult)
    }

    brandInfoRemover.remove(
      BrandInfoRemoveCommand(
        brandId,
        request.requester!!
      )
    )

    return ResponseEntity.accepted()
      .body("")
  }
}
