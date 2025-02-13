package com.musinsa.homework.jpa.entities.product

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.musinsa.homework.jpa.entities.brand.Brand
import com.musinsa.homework.jpa.entities.category.Category
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo

class ProductRepositoryImpl(
  private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
): ProductRepositoryCustom {

  override fun existsByBrandId(brandId: String): Boolean {
    val count = kotlinJdslJpqlExecutor.findAll {
      selectNew<Long>(
        count(path(Product::id)),
      ).from(
        entity(Product::class),
        join(entity(Brand::class)).on(path(Product::brand).eq(entity(Brand::class))),
      ).where(
        path(Brand::id).eq(brandId)
      )
    }.first()!!

    return count > 0
  }

  override fun findAllByBrandId(brandId: String): List<ProductInfo> {
    return kotlinJdslJpqlExecutor.findAll {
      selectNew<ProductInfo>(
        path(Product::id),
        path(Product::name),
        path(Category::id),
        path(Category::name),
        path(Product::price),
      ).from(
        entity(Product::class),
        join(entity(Category::class)).on(path(Product::category).eq(entity(Category::class))),
        join(entity(Brand::class)).on(path(Product::brand).eq(entity(Brand::class))),
      ).where(
        path(Brand::id).eq(brandId)
      ).orderBy(
        path(Product::name).asc()
      )
    }.filterNotNull()
  }
}
