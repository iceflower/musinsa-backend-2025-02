package com.musinsa.homework.components.brand

import com.musinsa.homework.components.brand.command.BrandInfoRemoveCommand
import com.musinsa.homework.components.exception.BrandHasProductsException
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class BrandInfoRemover(
  private val brandRepository: BrandRepository,
  private val productRepository: ProductRepository
) {

  /**
   * 브랜드 정보 삭제 명령서가 주어지면, 데이터베이스에 저장된 브랜드 정보를 삭제합니다.
   *
   * @param command 브랜드 정보 삭제 명령서
   * @throws BrandNotFoundException 삭제 대상 브랜드 정보를 찾을 수 없을 경우
   * @throws BrandHasProductsException 삭제 대상 브랜드의 상품이 존재할 경우
   */
  @RedisDistributedLock(key = "remove-brand-info")
  fun remove(command: BrandInfoRemoveCommand) {

    if (!brandRepository.existsById(command.brandId)) {
      throw BrandNotFoundException("브랜드 정보를 찾을 수 없습니다.")
    }

    if (productRepository.existsByBrandId(command.brandId)) {
      throw BrandHasProductsException("삭제하려는 브랜드의 상품이 존재합니다.")
    }

    brandRepository.deleteById(command.brandId)
  }
}
