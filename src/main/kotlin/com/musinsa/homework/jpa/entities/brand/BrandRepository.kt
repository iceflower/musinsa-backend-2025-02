package com.musinsa.homework.jpa.entities.brand

import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import org.springframework.data.jpa.repository.JpaRepository

interface BrandRepository : JpaRepository<Brand, String>, BrandRepositoryCustom

interface BrandRepositoryCustom {

  /**
   * 카데고리별 상품 가격 합계가 제일 저렴한 브랜드를 조회하여 그 결과를 돌려줍니다.
   *
   * @return 카테고리별 상품 가격 합계가 제일 저렴한 브랜드 정보
   */
  fun findLowPriceBrandName(): BrandInfo
}
