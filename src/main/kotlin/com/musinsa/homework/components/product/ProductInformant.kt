package com.musinsa.homework.components.product

import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class ProductInformant(
  private val productRepository: ProductRepository
) {

  /**
   * 제품 리스트 조회 명령서와 페이징 객체가 주어지면, 명령서 내 조회조건에 맞는 페이징 처리한 제품 리스트를 조회하여 돌려줍니다.
   *
   * @param command 제품 리스트 조회 명령서
   * @param pageRequest 페이징 객체
   * @return 제품 리스트 조회 명령서 내 조회조건에 맞는 페이징 처리한 제품 리스트
   */
  @RedisDistributedLock(key = "get-product-info-list")
  fun getList(command: ProductInfoListQueryCommand, pageRequest: PageRequest): Page<ProductInfo> {
    val totalCount = productRepository.count()
    val list = productRepository.findAllByQueryCommand(command, pageRequest)

    return PageImpl<ProductInfo>(list, pageRequest, totalCount)
  }

  /**
   * 제품 ID가 주어지면, 주어진 제품 ID에 대한 상품 정보를 조회하여 돌려줍니다.
   *
   * @param productId 제품ID
   * @return 제품 정보
   * @throws ProductNotFoundException 제품 정보가 존재하지 않을 경우
   */
  @RedisDistributedLock(key = "get-product-info")
  fun getProductInfo(productId: String) = productRepository.findById(productId)
    .orElseThrow { throw ProductNotFoundException() }
    .toVo()
}
