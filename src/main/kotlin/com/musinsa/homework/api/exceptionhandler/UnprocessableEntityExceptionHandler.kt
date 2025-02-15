package com.musinsa.homework.api.exceptionhandler

import com.musinsa.homework.api.exceptionhandler.response.ErrorResponse
import com.musinsa.homework.components.exception.AlreadyUsedBrandNameException
import com.musinsa.homework.components.exception.BrandHasProductsException
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.components.exception.ProductNotFoundException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class UnprocessableEntityExceptionHandler {

  /**
   * BrandNotFoundException 예외가 던져지면, 422 오류와 오류 메시지를 응답합니다.
   *
   * @param e BrandNotFoundException
   * @return 422 코드 및 오류 메시지
   */
  @ExceptionHandler(BrandNotFoundException::class)
  fun handleBrandNotFoundException(
    e: BrandNotFoundException
  ): ResponseEntity<ErrorResponse> {

    return ResponseEntity.unprocessableEntity()
      .body(
        ErrorResponse(
          "BRAND_INFO_NOT_FOUND",
          e.message!!
        )
      )
  }

  /**
   * CategoryNotFoundException 예외가 던져지면, 422 오류와 오류 메시지를 응답합니다.
   *
   * @param e CategoryNotFoundException
   * @return 422 코드 및 오류 메시지
   */
  @ExceptionHandler(CategoryNotFoundException::class)
  fun handleCategoryNotFoundException(
    e: CategoryNotFoundException
  ): ResponseEntity<ErrorResponse> {

    return ResponseEntity.unprocessableEntity()
      .body(
        ErrorResponse(
          "CATEGORY_INFO_NOT_FOUND",
          e.message!!
        )
      )
  }

  /**
   * ProductNotFoundException 예외가 던져지면, 422 오류와 오류 메시지를 응답합니다.
   *
   * @param e ProductNotFoundException
   * @return 422 코드 및 오류 메시지
   */
  @ExceptionHandler(ProductNotFoundException::class)
  fun handleProductNotFoundException(
    e: ProductNotFoundException
  ): ResponseEntity<ErrorResponse> {

    return ResponseEntity.unprocessableEntity()
      .body(
        ErrorResponse(
          "PRODUCT_INFO_NOT_FOUND",
          e.message!!
        )
      )
  }

  /**
   * BrandHasProductsException 예외가 던져지면, 400 오류와 오류 메시지를 응답합니다.
   *
   * @param e BrandHasProductsException
   * @return 422 코드 및 오류 메시지
   */
  @ExceptionHandler(BrandHasProductsException::class)
  fun handleBrandHasProductsException(
    e: BrandHasProductsException
  ): ResponseEntity<ErrorResponse> {

    return ResponseEntity.unprocessableEntity()
      .body(
        ErrorResponse(
          "BRAND_INFO_HAS_PRODUCTS",
          e.message!!
        )
      )
  }

  /**
   * AlreadyUsedBrandNameException 예외가 던져지면, 400 오류와 오류 메시지를 응답합니다.
   *
   * @param e AlreadyUsedBrandNameException
   * @return 422 코드 및 오류 메시지
   */
  @ExceptionHandler(AlreadyUsedBrandNameException::class)
  fun handleAlreadyUsedBrandNameException(
    e: AlreadyUsedBrandNameException
  ): ResponseEntity<ErrorResponse> {

    return ResponseEntity.unprocessableEntity()
      .body(
        ErrorResponse(
          "GIVEN_BRAND_NAME_IS_ALREADY_USING",
          e.message!!
        )
      )
  }
}
