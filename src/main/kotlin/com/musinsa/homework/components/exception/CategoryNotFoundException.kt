package com.musinsa.homework.components.exception

/**
 * 카테고리 정보를 찾을 수 없을 때 던져지는 예외를 정의합니다.
 */
class CategoryNotFoundException(override val message: String?) : RuntimeException(message)
