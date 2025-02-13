package com.musinsa.homework.jpa.entities.product

import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface ProductRepository : JpaRepository<Product, String>, ProductRepositoryCustom {

  @EntityGraph(attributePaths = ["brand", "category"])
  override fun findById(id: String): Optional<Product>

}

interface ProductRepositoryCustom {

  /**
   * 브랜드 ID가 주어지면, 그 브랜드의 상품이 존재하는지를 조회하여 그 결과를 돌려줍니다.
   *
   * @param brandId 브랜드ID
   * @return 브랜드의 상품이 존재하면 true, 존재하지 않으면 false
   */
  fun existsByBrandId(brandId: String): Boolean

  /**
   * 제품 리스트 조회 명령서가 주어지면, 명령서 내 조회조건 데이터 기준으로 제품 리스트를 조회하여 돌려줍니다.
   *
   * @param command 제품 리스트 조회 명령서
   * @return 제품 리스트
   */
  fun findAllByQueryCommand(command: ProductInfoListQueryCommand): List<ProductInfo>

  /**
   * 제품 리스트 조회 명령서와 페이징 객체가 주어지면, 명령서 내 조회조건 데이터 기준으로 페이징 처리한 제품 리스트를 조회하여 돌려줍니다.
   *
   * @param command 제품 리스트 조회 명령서
   * @param pageRequest 페이징 객체
   * @return 제품 리스트
   */
  fun findAllByQueryCommand(command: ProductInfoListQueryCommand, pageRequest: PageRequest): List<ProductInfo>
}
