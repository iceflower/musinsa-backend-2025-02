package com.musinsa.homework.jpa.entities.product

import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, String>, ProductRepositoryCustom

interface ProductRepositoryCustom {

  /**
   * 브랜드 ID가 주어지면, 그 브랜드의 카테고리별 상품 리스트를 조회하여 돌려줍니다.
   *
   * @param brandId 브랜드ID
   * @return 카테고리별 상품 리스트
   */
  fun findAllByBrandId(brandId: String): List<ProductInfo>
}
