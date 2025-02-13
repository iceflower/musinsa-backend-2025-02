package com.musinsa.homework.components.exception

/**
 * 브랜드의 상품이 존재할 경우 던져지는 예외를 정의합니다.
 */
class BrandHasProductsException(override val message: String?) : RuntimeException(message)
