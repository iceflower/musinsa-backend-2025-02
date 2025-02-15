package com.musinsa.homework.components.product

import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.components.exception.CategoryNotFoundException
import com.musinsa.homework.components.exception.ProductNotFoundException
import com.musinsa.homework.components.product.command.ProductInfoCorrectCommand
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.category.CategoryRepository
import com.musinsa.homework.jpa.entities.product.ProductRepository
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class ProductInfoCorrector(
  private val brandRepository: BrandRepository,
  private val categoryRepository: CategoryRepository,
  private val productRepository: ProductRepository
) {

  /**
   * 제품 정보 수정 명령서가 주어지면, 명령서 내 정보를 활용하여 제품 정보를 수정한 후, 그 결과를 돌려줍니다.
   *
   * @param command 제품 정보 수정 명령서
   * @return 수정된 제품 정보
   * @throws BrandNotFoundException 브랜드 정보를 찾을 수 없을 경우
   * @throws CategoryNotFoundException 카테고리 정보를 찾을 수 없을 경우
   * @throws ProductNotFoundException 제품 정보를 찾을 수 없을 경우
   */
  @RedisDistributedLock(key = "edit-product-info")
  fun correct(command: ProductInfoCorrectCommand): ProductInfo {

    val brand = brandRepository.findById(command.brandId)
      .orElseThrow { throw BrandNotFoundException() }

    val category = categoryRepository.findById(command.categoryId)
      .orElseThrow { throw CategoryNotFoundException() }

    val storedProduct = productRepository.findById(command.productId)
      .orElseThrow { throw ProductNotFoundException() }

    storedProduct.editProductBrand(brand, command.requester)
    storedProduct.editProductCategory(category, command.requester)
    storedProduct.editProductName(command.productName, command.requester)
    storedProduct.editProductPrice(command.productPrice, command.requester)

    return productRepository.save(storedProduct)
      .toVo()
  }
}
