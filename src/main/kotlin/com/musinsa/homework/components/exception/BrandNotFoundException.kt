package com.musinsa.homework.components.exception

/**
 * 브랜드 정보를 찾을 수 없을 때 던져지는 예외를 정의합니다.
 */
class BrandNotFoundException(override val message: String?) : RuntimeException(message)
