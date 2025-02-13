package com.musinsa.homework.components.brand

import com.musinsa.homework.components.brand.command.BrandInfoCorrectCommand
import com.musinsa.homework.components.exception.BrandNotFoundException
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class BrandInfoCorrector(
  private val brandRepository: BrandRepository
) {

  /**
   * 브랜드 정보 정정 명령서가 주어지면, 데이터베이스에 저장된 브랜드 정보를 수정합니다.
   *
   * @param command 브랜드 정보 정정 명령서
   * @return 수정된 브랜드 정보 객체
   * @throws BrandNotFoundException 수정 대상 브랜드 정보를 찾을 수 없을 경우
   */
  @RedisDistributedLock(key = "edit-brand-info")
  fun correct(command: BrandInfoCorrectCommand): BrandInfo {

    if (!brandRepository.existsById(command.brandId)) {
      throw BrandNotFoundException("브랜드 정보를 찾을 수 없습니다.")
    }

    val storedEntity = brandRepository.findById(command.brandId).get()
    storedEntity.editBrandName(command.brandName, command.requester)

    return brandRepository.save(storedEntity)
      .toVo()
  }
}
