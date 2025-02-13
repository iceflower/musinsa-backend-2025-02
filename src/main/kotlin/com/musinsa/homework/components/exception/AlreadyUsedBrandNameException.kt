package com.musinsa.homework.components.exception

/**
 * 주어진 브랜드명이 이미 사용중인 브랜드명일 경우 던져지는 예외를 정의합니다.
 */
class AlreadyUsedBrandNameException(override val message: String?) : RuntimeException(message)
