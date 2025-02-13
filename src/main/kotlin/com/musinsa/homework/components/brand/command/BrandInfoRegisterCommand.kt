package com.musinsa.homework.components.brand.command

/**
 * 브랜드 등록 명령서 객체.
 *
 * @property brandName 브랜드명
 * @property requester 등록 요청자
 */
data class BrandInfoRegisterCommand(
  val brandName: String,
  val requester: String
)
