package com.musinsa.homework.jpa.entities.brand

import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, String>, BrandRepositoryCustom {

  /**
   * 브랜드명이 주어지면, 주어진 브랜드명이 사용중인지 확인하여 그 결과를 돌려줍니다.
   *
   * @param name 조회대상 브랜드명
   * @return 주어진 브랜드명이 사용중이면 true, 그렇지 않으면 false
   */
  fun existsByName(name: String): Boolean
}

interface BrandRepositoryCustom {

  /**
   * 카데고리별 상품 가격 합계가 제일 저렴한 브랜드를 조회하여 그 결과를 돌려줍니다.
   *
   * @return 카테고리별 상품 가격 합계가 제일 저렴한 브랜드 정보
   */
  fun findLowPriceBrandName(): BrandInfo

  /**
   * 페이징 객체가 주어지면, 페이징 처리한 브랜드 리스트를 조회하여 그 결과를 돌려줍니다.
   *
   * @param pageRequest 페이징 객체
   * @return 페이징 처리한 브랜드 리스트
   */
  fun getAllPagedList(pageRequest: PageRequest): List<BrandInfo>
}
