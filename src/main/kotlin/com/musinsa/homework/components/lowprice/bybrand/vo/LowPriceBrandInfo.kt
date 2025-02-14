package com.musinsa.homework.components.lowprice.bybrand.vo


/**
 *  특정 브랜드의 제품만 구매했을 때의 최저가로 구매 가능한 브랜드의 제품 리스트와 가격 합계 정보 객체.
 *
 *  @property lowestPrice 최저가 브랜드 정보
 */
data class LowPriceBrandInfo(
  val lowestPrice: BrandPriceInfo
) {

  companion object {
    /**
     * LowPriceBrandInfo 객체 생성을 위한 정적 팩토리 메소드입니다.
     *
     * @param brandName 브랜드명
     * @param brandProductsByCategories 카테고리별 제품 정보
     * @return LowPriceBrandInfo
     */
    fun create(brandName: String, brandProductsByCategories: List<ProductInfo>): LowPriceBrandInfo {
      val totalPrice = brandProductsByCategories.distinctBy { it.categoryName }
        .sumOf { it.price }

      return LowPriceBrandInfo(
        BrandPriceInfo(
          brandName,
          totalPrice,
          brandProductsByCategories
        )

      )
    }
  }
}

/**
 * 특정 브랜드의 제품만 구매했을 때의 제품 리스트와 가격 합계 정보 객체.
 *
 * @property brandName 브랜드명
 * @property totalPrice 제품 가격 합계
 * @property products  카테고리별 제품 정보
 */
data class BrandPriceInfo(
  val brandName: String,
  val totalPrice: Long,
  val products: List<ProductInfo>,
)

/**
 * 카테고리별 제품 명세서 객체.
 *
 * @property categoryName 카테고리명
 * @property price 제품 가격
 */
data class ProductInfo(
  val categoryName: String,
  val price: Long
)


