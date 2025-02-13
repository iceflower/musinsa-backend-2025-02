package com.musinsa.homework.components.lowprice.bybrand

import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class LowestPriceBrandInformant(
  private val brandRepository: BrandRepository,
  private val productRepository: ProductRepository
) {

  /**
   * 특정 브랜드 제품만 골랐을 때, 최저가로 구매 가능한 브랜드 및 상품 정보를 조회하여 돌려줍니다.
   *
   * @return 최저가로 구매 가능한 브랜드 및 브랜드 상품 리스트
   */
  @RedisDistributedLock(key = "get-lowest-price-brand", readOnly = true)
  fun getLowPriceBrandInfo(): LowPriceBrandStatement {
    val brandInfo = brandRepository.findLowPriceBrandName()

    val command = ProductInfoListQueryCommand(
      brandInfo.id, null, null, null,
    )

    val productStatements = productRepository.findAllByQueryCommand(command)
      .map { ProductStatement(it.categoryName, it.productPrice) }
    return LowPriceBrandStatement(brandInfo.name, productStatements)
  }
}
