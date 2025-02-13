package com.musinsa.homework.components.product

import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoRegisterCommand
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.jpa.entities.product.Product
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class ProductInfoRegistrar(
  private val brandRepository: BrandRepository,
  private val categoryRepository: CategoryRepository,
  private val productRepository: ProductRepository
) {

  /**
   * 제품 등록 명령서가 주어지면, 주어진 명령서 내 정보를 활용해 제품 정보를 등록합니다.
   *
   * @param command 제품 등록 명령서
   * @return 등록된 제품 정보
   * @throws BrandNotFoundException 브랜드 정보를 찾을 수 없을 경우
   * @throws CategoryNotFoundException 카테고리 정보를 찾을 수 없을 경우
   */
  @RedisDistributedLock(key = "add-product-info")
  fun register(command: ProductInfoRegisterCommand): ProductInfo {

    val brand = brandRepository.findById(command.brandId)
      .orElseThrow { throw BrandNotFoundException("브랜드 정보를 찾을 수 없습니다.") }

    val category = categoryRepository.findById(command.categoryId)
      .orElseThrow { throw CategoryNotFoundException("카테고리 정보를 찾을 수 없습니다.") }

    val newEntity = Product.create(
      command.productName,
      command.productPrice,
      brand,
      category,
      command.requester
    )

    return productRepository.save(newEntity)
      .toVo()
  }
}
