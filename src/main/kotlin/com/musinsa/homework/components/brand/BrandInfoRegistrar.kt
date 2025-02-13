package com.musinsa.homework.components.brand

import com.musinsa.homework.components.brand.command.BrandInfoRegisterCommand
import com.musinsa.homework.components.exception.AlreadyUsedBrandNameException
import com.musinsa.homework.jpa.entities.brand.Brand
import com.musinsa.homework.jpa.entities.brand.BrandRepository
import com.musinsa.homework.jpa.entities.brand.vo.BrandInfo
import com.musinsa.homework.util.lock.distributed.RedisDistributedLock
import org.springframework.stereotype.Service

@Service
class BrandInfoRegistrar(
  private val brandRepository: BrandRepository
) {

  /**
   * 브랜드 등록 명령서가 주어지면 새로운 브랜드 정보를 등록합니다.
   *
   * @param command 브랜드 등록 명령서 객체
   * @return 등록된 브랜드 정보 객체
   * @throws AlreadyUsedBrandNameException 명령서를 통해 전달받은 브랜드명이 이미 사용중인 브랜드명일 경우
   */
  @RedisDistributedLock(key = "register-brand-info")
  fun register(command: BrandInfoRegisterCommand): BrandInfo {

    if (brandRepository.existsByName(command.brandName)) {
      throw AlreadyUsedBrandNameException("이미 사용중인 브랜드명입니다.")
    }

    return brandRepository.save(Brand.create(command.brandName, command.requester))
      .toVo()
  }
}
