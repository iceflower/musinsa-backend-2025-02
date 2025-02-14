package com.musinsa.homework.api.price

import com.musinsa.homework.api.price.response.LowestAndHighestPriceProductsByCategoryResponse
import com.musinsa.homework.api.price.response.ProductInfo
import com.musinsa.homework.components.lowprice.byallcategories.LowestPriceByAllCategoriesInformant
import com.musinsa.homework.components.lowprice.byallcategories.vo.LowPriceByCategoriesInfo
import com.musinsa.homework.components.lowprice.bybrand.LowestPriceBrandInformant
import com.musinsa.homework.components.lowprice.bybrand.vo.LowPriceBrandInfo
import com.musinsa.homework.components.lowprice.eachcategories.LowestPriceByEachCategoriesInformant
import com.musinsa.homework.components.maxprice.eachcategories.HighestPriceByEachCategoriesInformant
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/price")
class PriceController(
  private val lowestPriceByAllCategoriesInformant: LowestPriceByAllCategoriesInformant,
  private val lowestPriceBrandInformant: LowestPriceBrandInformant,
  private val lowestPriceByEachCategoriesInformant: LowestPriceByEachCategoriesInformant,
  private val highestPriceByEachCategoriesInformant: HighestPriceByEachCategoriesInformant
) {

  /**
   * 카테고리별 최저가 상품 리스트를 조회하여 그 결과를 돌려줍니다.
   *
   * @return 카테고리별 최저가 상품 정보 리스트
   */
  @GetMapping("/lowest-by-cagegories-list")
  fun getLowestByCategoriesList(): ResponseEntity<LowPriceByCategoriesInfo> {
    return ResponseEntity.ok(lowestPriceByAllCategoriesInformant.getLowestPriceBrandByCategories())
  }

  /**
   * 단일 브랜드 제품을 골랐을 경우 최저가로 구매 가능한 카테고리별 상품 리스트를 조회하여 그 결과를 돌려줍니다.
   *
   * @return 단일 브랜드 제품을 골랐을 경우 최저가로 구매 가능한 카테고리별 상품 리스트
   */
  @GetMapping("/lowest-by-cagegory-list/when-choosing-a-single-brand")
  fun getLowestByCategoriesWhenChoosingASingleBrand(): ResponseEntity<LowPriceBrandInfo> {
    return ResponseEntity.ok(lowestPriceBrandInformant.getLowPriceBrandInfo())
  }

  /**
   * 카테고리 이름이 주어지면, 주어진 카테고리 내 최저가 제품과 최고가 제품을 조회하여 그 결과를 돌려줍니다.
   *
   * @param categoryName 카테고리명
   * @return 주어진 카테고리 내 최저가 제품과 최고가 제품 정보
   *
   * @throws com.musinsa.homework.components.exception.CategoryNotFoundException 카테고리 정보를 찾을 수 없을 경우
   */
  @GetMapping("/lowest-and-highest-info/of-category/{categoryName}")
  fun getLowestPriceAndHighestPriceProductsOfCategory(
    @PathVariable("categoryName") categoryName: String
  ): ResponseEntity<LowestAndHighestPriceProductsByCategoryResponse> {
    val lowestPrice = lowestPriceByEachCategoriesInformant.getLowestPriceByEachCategory(categoryName)
      .map { ProductInfo(it.brandName, it.price) }
    val highestPrice = highestPriceByEachCategoriesInformant.getHighestPriceByEachCategory(categoryName)
      .map { ProductInfo(it.brandName, it.price) }
    return ResponseEntity.ok(
      LowestAndHighestPriceProductsByCategoryResponse(
        categoryName,
        lowestPrice,
        highestPrice
      )
    )
  }
}
