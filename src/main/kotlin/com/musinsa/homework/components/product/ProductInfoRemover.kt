package com.musinsa.homework.components.product

import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoRemoveCommand
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class ProductInfoRemover(
  private val productRepository: ProductRepository
) {

  /**
   * 제품 삭제 명령서가 주어지면, 제품 삭제 명령서 내 정보를 활용해 제품 정보를 삭제합니다.
   *
   * @param command 제품 삭제 명령서
   * @throws ProductNotFoundException 삭제 대상 제품 정보가 존재하지 않을 경우
   */
  @RedisDistributedLock(key = "delete-product-info")
  fun remove(command: ProductInfoRemoveCommand) {

    if (!productRepository.existsById(command.productId)) {
      throw ProductNotFoundException("제품 정보를 찾을 수 없습니다.")
    }
    productRepository.deleteById(command.productId)
  }
}
