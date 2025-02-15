package com.musinsa.homework.components.exception

/**
 * 제품 정보를 찾을 수 없을 때 던져지는 예외를 정의합니다.
 */
class ProductNotFoundException(override val message: String?) : RuntimeException(message) {
  constructor() : this("제품 정보를 찾을 수 없습니다.")
}
