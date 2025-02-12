package com.musinsa.homework.jpa.entities.product

import com.musinsa.homework.jpa.entities.brand.Brand
import com.musinsa.homework.jpa.entities.category.Category
import com.musinsa.homework.jpa.entities.common.AuditEntity
import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

/**
 * 제품 정보 엔티티.
 *
 * @property id 제품 정보 ID (PK, TSID)
 * @property name 제품 이름
 * @property price 제품 가격
 * @property brand 브랜드 엔티티
 * @property category 카테고리 엔티티
 * @property createdAt 최초작성시각
 * @property createdBy 최초작성자
 * @property updatedAt 최종수정시각
 * @property updatedBy 최종수정자
 *
 * @see AuditEntity
 * @see Brand
 * @see Category
 */
@Entity
@Table(name = "product")
class Product() : AuditEntity() {

  @Id
  @Tsid
  @Column(name = "product_id", nullable = false)
  var id: String? = null

  @Column(name = "product_name", nullable = false)
  lateinit var name: String

  @Column(name = "product_price", nullable = false)
  var price: Long = 0

  @ManyToOne
  @JoinColumn(
    name = "brand_id",
    referencedColumnName = "brand_id",
    nullable = false
  )
  lateinit var brand: Brand

  @ManyToOne
  @JoinColumn(
    name = "category_id",
    referencedColumnName = "category_id",
    nullable = false
  )
  lateinit var category: Category

  companion object {

    /**
     * 새로운 제품 엔티티를 생성합니다.
     *
     * @param productName 제품이름
     * @param productPrice 제품가격
     * @param productBrand 제품의 브랜드
     * @param productCategory 제품의 카테고리
     * @param requester 생성 요청자
     *
     * @return 신규 생성된 제품 엔티티
     */
    fun create(
      productName: String,
      productPrice: Long,
      productBrand: Brand,
      productCategory: Category,
      requester: String
    ): Product {
      val now = LocalDateTime.now()
      val newEntity = Product()
      newEntity.name = productName
      newEntity.price = productPrice
      newEntity.brand = productBrand
      newEntity.category = productCategory
      newEntity.createdBy = requester
      newEntity.createdAt = now
      newEntity.updatedBy = requester
      newEntity.updatedAt = now

      return newEntity
    }
  }

  /**
   * 제품 이름을 수정합니다.
   *
   * @param productName 변경할 제품 이름
   * @param requester 변경 요청자
   */
  fun editProductName(productName: String, requester: String) {
    this.name = productName
    this.updatedBy = requester
    this.updatedAt = LocalDateTime.now()
  }

  /**
   * 제품 가격을 수정합니다.
   *
   * @param productPrice 변경할 제품 가격
   * @param requester 변경 요청자
   */
  fun editProductPrice(productPrice: Long, requester: String) {
    this.price = productPrice
    this.updatedBy = requester
    this.updatedAt = LocalDateTime.now()
  }

  /**
   * 제품 브랜드를 수정합니다.
   *
   * @param productBrand 변경할 제품 브랜드 엔티티
   * @param requester 변경 요청자
   */
  fun editProductBrand(productBrand: Brand, requester: String) {
    this.brand = productBrand
    this.updatedBy = requester
    this.updatedAt = LocalDateTime.now()
  }

  /**
   * 제품 카테고리를 수정합니다.
   *
   * @param productCategory 변경할 제품 가테고리 엔티티
   * @param requester 변경 요청자
   */
  fun editProductCategory(productCategory: Category, requester: String) {
    this.category = productCategory
    this.updatedBy = requester
    this.updatedAt = LocalDateTime.now()
  }
}
