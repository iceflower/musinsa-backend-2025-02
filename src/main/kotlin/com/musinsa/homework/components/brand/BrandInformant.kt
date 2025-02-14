package com.musinsa.homework.components.brand

import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class BrandInformant(
  private val brandRepository: BrandRepository
) {

  /**
   * 페이징 정보 객체가 주어지면, 주어진 페이징 정보대로 페이징 처리한 브랜드 정보 리스트를 돌려줍니다.
   *
   * @param pageRequest 페이징 정보 객체
   * @return 페이징 처리한 브랜드 정보 리스트
   */
  @RedisDistributedLock(key = "get-paged-brand-list", readOnly = true)
  fun getList(pageRequest: PageRequest): Page<BrandInfo> {
    val totalCount = brandRepository.count()
    val pagedList = brandRepository.getAllPagedList(pageRequest)
    return PageImpl<BrandInfo>(pagedList, pageRequest, totalCount)
  }

  /**
   * 브랜드 ID가 주어지면, 브랜드 정보를 조회하여 그 결과를 돌려줍니다.
   *
   * @param brandId 브랜드ID
   * @return 브랜드 정보
   * @throws BrandNotFoundException 브랜드 정보를 찾을 수 없을 경우
   */
  @RedisDistributedLock(key = "get-brand-info", readOnly = true)
  fun getBrandInfo(brandId: String) = brandRepository.findById(brandId)
    .orElseThrow { throw BrandNotFoundException("브랜드 정보를 찾을 수 없습니다.") }
    .toVo()
}
