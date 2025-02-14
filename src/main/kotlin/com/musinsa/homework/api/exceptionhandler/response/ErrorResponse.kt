package com.musinsa.homework.api.exceptionhandler.response

/**
 * 오류 응답 객체.
 *
 * @property code 오류코드
 * @property message 오류메시지
 */
data class ErrorResponse(
  val code: String,
  val message: String
)
