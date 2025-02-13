package com.musinsa.homework.jpa.entities.product

import com.linecorp.kotlinjdsl.dsl.jpql.Jpql
import com.linecorp.kotlinjdsl.querymodel.jpql.JpqlQueryable
import com.linecorp.kotlinjdsl.querymodel.jpql.predicate.Predicate
import com.linecorp.kotlinjdsl.querymodel.jpql.select.SelectQuery
import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.jpa.entities.brand.Brand
import com.musinsa.homework.jpa.entities.category.Category
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import org.springframework.data.domain.PageRequest

class ProductRepositoryImpl(
  private val kotlinJdslJpqlExecutor: KotlinJdslJpqlExecutor,
) : ProductRepositoryCustom {

  override fun existsByBrandId(brandId: String): Boolean {
    val count = kotlinJdslJpqlExecutor.findAll {
      selectNew<Long>(
        count(path(Product::id)),
      ).from(
        entity(Product::class),
        fetchJoin(entity(Brand::class)).on(path(Product::brand)(Brand::id).eq(path(Brand::id))),
      ).where(
        path(Product::brand)(Brand::id).eq(brandId)
      )
    }.first()!!

    return count > 0
  }

  override fun findAllByQueryCommand(command: ProductInfoListQueryCommand): List<ProductInfo> {
    return kotlinJdslJpqlExecutor.findAll {
      getJpqlQuery(command)
    }.filterNotNull()
  }

  override fun findAllByQueryCommand(
    command: ProductInfoListQueryCommand,
    pageRequest: PageRequest
  ): List<ProductInfo> {
    return kotlinJdslJpqlExecutor.findAll(pageRequest) {
      getJpqlQuery(command)
    }.filterNotNull()
  }


  /**
   * 리스트 조회를 위한 jpql 쿼리.
   */
  private fun Jpql.getJpqlQuery(command: ProductInfoListQueryCommand): JpqlQueryable<SelectQuery<ProductInfo>> {
    return selectNew<ProductInfo>(
      path(Product::id),
      path(Product::name),
      path(Product::category)(Category::id),
      path(Product::category)(Category::name),
      path(Product::brand)(Brand::id),
      path(Product::brand)(Brand::name),
      path(Product::price),
    ).from(
      entity(Product::class),
      fetchJoin(entity(Category::class)).on(path(Product::category)(Category::id).eq(path(Category::id))),
      fetchJoin(entity(Brand::class)).on(path(Product::brand)(Brand::id).eq(path(Brand::id)))
    ).whereAnd(
      eqBrandId(command.brandId),
      eqCategoryId(command.categoryId),
      gePriceFrom(command.priceFrom),
      lePriceTo(command.priceTo)
    ).orderBy(
      path(Product::name).asc()
    )
  }

  /**
   * 조회조건 - 브랜드 ID 일치여부.
   */
  private fun Jpql.eqBrandId(brandId: String?): Predicate? {
    if (brandId == null || brandId.isEmpty()) {
      return null
    }

    return path(Product::brand)(Brand::id).eq(brandId)
  }

  /**
   * 조회조건 - 카테고리 ID 일치여부.
   */
  private fun Jpql.eqCategoryId(categoryId: String?): Predicate? {
    if (categoryId == null || categoryId.isEmpty()) {
      return null
    }

    return path(Product::category)(Category::id).eq(categoryId)
  }

  /**
   * 조회조건 - 가격 From
   */
  private fun Jpql.gePriceFrom(priceFrom: Long?): Predicate? {
    if (priceFrom == null) {
      return null
    }

    return path(Product::price).ge(priceFrom)
  }

  /**
   * 조회조건 - 가격 To
   */
  private fun Jpql.lePriceTo(priceTo: Long?): Predicate? {
    if (priceTo == null) {
      return null
    }

    return path(Product::price).le(priceTo)
  }
}
