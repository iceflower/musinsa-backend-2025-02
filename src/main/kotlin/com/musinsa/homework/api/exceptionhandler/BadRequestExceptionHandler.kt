package com.musinsa.homework.api.exceptionhandler

import com.musinsa.homework.api.exceptionhandler.response.ArgumentValidationErrorResponse
import com.musinsa.homework.api.exceptionhandler.response.DetailInfo
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class BadRequestExceptionHandler {

  /**
   * BindException 예외가 던져지면, 400 오류와 오류 메시지를 응답합니다.
   *
   * @param e BindException
   * @return 400 코드 및 오류 메시지
   */
  @ExceptionHandler(BindException::class)
  fun handleBindException(
    e: BindException
  ): ResponseEntity<ArgumentValidationErrorResponse> {

    val detailInfo = e.fieldErrors.map {
      DetailInfo(
        it.rejectedValue!!,
        it.defaultMessage!!
      )
    }

    return ResponseEntity.badRequest()
      .body(
        ArgumentValidationErrorResponse(
          "INVALID_REQUEST_DATA_FORMAT",
          "요청 데이터 포맷이 유효하지 않습니다.",
          detailInfo
        )
      )
  }
}
