package com.musinsa.homework.jpa.entities.brand

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import com.musinsa.homework.jpa.entities.product.Product
import org.springframework.data.domain.PageRequest

class BrandRepositoryImpl(
  private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
) : BrandRepositoryCustom {
  override fun findLowPriceBrandName(): BrandInfo {
    return kotlinJdslJpqlExecutor.findAll {
      selectNew<BrandInfo>(
        path(Brand::id),
        path(Brand::name),
      ).from(
        entity(Brand::class),
        join(entity(Product::class)).on(path(Product::brand).eq(entity(Brand::class)))
      ).groupBy(
        path(Brand::id), path(Brand::name)
      ).orderBy(
        sum(path(Product::price)).asc()
      )
    }.first()!!
  }

  override fun getAllPagedList(pageRequest: PageRequest) = kotlinJdslJpqlExecutor.findPage(pageRequest) {
    selectNew<BrandInfo>(
      path(Brand::id),
      path(Brand::name),
    ).from(
      entity(Brand::class),
    ).orderBy(
      path(Brand::id).asc()
    )
  }.filterNotNull()
}
