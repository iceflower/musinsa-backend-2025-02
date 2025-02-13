package com.musinsa.homework.components.brand.command

/**
 * 브랜드 정보 삭제 명령서 객체.
 *
 * @property brandId 삭제대상 브랜드 ID
 * @property requester 등록 요청자
 */
data class BrandInfoRemoveCommand(
  val brandId: String,
  val requester: String
)
