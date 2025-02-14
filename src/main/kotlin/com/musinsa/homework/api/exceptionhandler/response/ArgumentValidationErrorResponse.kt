package com.musinsa.homework.api.exceptionhandler.response

/**
 * 매개변수 유효성 검사 오류 응답 객체.
 *
 * @property code 오류코드
 * @property message 오류메시지
 * @property detailed 오류 상세 정보
 */
data class ArgumentValidationErrorResponse(
  val code: String,
  val message: String,
  val detailed: List<DetailInfo>
)

/**
 * 오류 상세정보.
 *
 * @property value 오류가 발생한 값
 * @property message 오류메시지
 */
data class DetailInfo(
  val value: Any,
  val message: String
)
