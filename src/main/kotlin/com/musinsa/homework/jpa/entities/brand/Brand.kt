package com.musinsa.homework.jpa.entities.brand

import com.musinsa.homework.jpa.entities.common.AuditEntity
import com.musinsa.homework.jpa.entities.product.Product
import io.hypersistence.utils.hibernate.id.Tsid
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDateTime

/**
 * 브랜드 정보 엔티티.
 *
 * @property id 브랜드 정보 ID (PK, TSID)
 * @property name 브랜드 이름
 * @property products 제품 리스트
 * @property createdAt 최초작성시각
 * @property createdBy 최초작성자
 * @property updatedAt 최종수정시각
 * @property updatedBy 최종수정자
 *
 * @see AuditEntity
 * @see Product
 */
@Entity
class Brand() : AuditEntity() {

  @Id
  @Tsid
  @Column(name = "brand_id", nullable = false)
  var id: String? = null

  @Column(name = "brand_name", nullable = false)
  lateinit var name: String

  @OneToMany(
    mappedBy = "product",
    fetch = FetchType.LAZY,
    orphanRemoval = true,
  )
  lateinit var products: MutableList<Product>

  companion object {

      /**
     * 새로운 브랜드 엔티티를 생성합니다.
     *
     * @param brandName 브랜드 이름
     * @param requester 생성 요청자
     *
     * @return 신규 생성된 브랜드 엔티티
     */
    fun create(brandName: String, requester: String): Brand {
      val now = LocalDateTime.now()
      val newEntity = Brand()
      newEntity.name = brandName
      newEntity.products = mutableListOf<Product>()
      newEntity.createdBy = requester
      newEntity.createdAt = now
      newEntity.updatedBy = requester
      newEntity.updatedAt = now

      return newEntity
    }
  }

  /**
   * 브랜드 이름을 수정합니다.
   *
   * @param brandName 변경할 브랜드 이름
   * @param requester 변경 요청자
   */
  fun editBrandName(brandName: String, requester: String) {
    this.name = brandName
    this.updatedBy = requester
    this.updatedAt = LocalDateTime.now()
  }
}
