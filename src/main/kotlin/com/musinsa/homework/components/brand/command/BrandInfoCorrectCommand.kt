package com.musinsa.homework.components.brand.command

/**
 * 브랜드 정보 정정 명령서 객체.
 *
 * @property brandId 수정대상 브랜드 ID
 * @property brandName 브랜드명
 * @property requester 등록 요청자
 */
data class BrandInfoCorrectCommand(
  val brandId: String,
  val brandName: String,
  val requester: String
)
