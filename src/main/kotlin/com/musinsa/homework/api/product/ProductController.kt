package com.musinsa.homework.api.product

import com.musinsa.homework.api.product.request.ProductInfoCorrectRequest
import com.musinsa.homework.api.product.request.ProductInfoRegisterRequest
import com.musinsa.homework.components.product.ProductInfoCorrector
import com.musinsa.homework.components.product.ProductInfoRegistrar
import com.musinsa.homework.components.product.ProductInfoRemover
import com.musinsa.homework.components.product.ProductInformant
import com.musinsa.homework.components.product.command.ProductInfoCorrectCommand
import com.musinsa.homework.components.product.command.ProductInfoListQueryCommand
import com.musinsa.homework.components.product.command.ProductInfoRegisterCommand
import com.musinsa.homework.components.product.command.ProductInfoRemoveCommand
import com.musinsa.homework.jpa.entities.product.vo.ProductInfo
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/api/product")
class ProductController(
  private val productInformant: ProductInformant,
  private val productInfoRegistrar: ProductInfoRegistrar,
  private val productInfoCorrector: ProductInfoCorrector,
  private val productInfoRemover: ProductInfoRemover,
) {

  @GetMapping("/list")
  fun getList(
    @RequestParam(name = "brandId", required = false) brandId: String?,
    @RequestParam(name = "categoryId", required = false) categoryId: String?,
    @RequestParam(name = "priceFrom", required = false) priceFrom: Long?,
    @RequestParam(name = "priceTo", required = false) priceTo: Long?,
    @RequestParam(name = "pageNumber", required = false, defaultValue = "0") pageNumber: Int,
    @RequestParam(name = "pageSize", required = false, defaultValue = "10") pageSize: Int,
  ): Page<ProductInfo> {

    return productInformant.getList(
      ProductInfoListQueryCommand(
        brandId,
        categoryId,
        priceFrom,
        priceTo,
      ),
      PageRequest.of(pageNumber, pageSize)
    )
  }

  @GetMapping("/{productId}")
  fun getInfo(@PathVariable("productId") productId: String): ProductInfo {

    return productInformant.getProductInfo(productId)
  }

  @PatchMapping("/{productId}")
  fun correct(
    @PathVariable("productId") productId: String,
    @RequestBody @Valid request: ProductInfoCorrectRequest
  ): ProductInfo {

    return productInfoCorrector.correct(
      ProductInfoCorrectCommand(
        productId,
        request.brandId!!,
        request.categoryId!!,
        request.productName!!,
        request.productPrice!!,
        request.requester!!
      )
    )
  }

  @PostMapping
  fun register(
    @RequestBody @Valid request: ProductInfoRegisterRequest
  ): ProductInfo {

    return productInfoRegistrar.register(
      ProductInfoRegisterCommand(
        request.brandId!!,
        request.categoryId!!,
        request.productName!!,
        request.productPrice!!,
        request.requester!!
      )
    )
  }

  @DeleteMapping("/{productId}")
  fun remove(
    @PathVariable("productId") productId: String,
    @RequestBody @Valid request: ProductInfoRegisterRequest
  ) {

    return productInfoRemover.remove(
      ProductInfoRemoveCommand(
        productId,
        request.requester!!
      )
    )
  }
}
