package com.musinsa.homework.util.lock.distributed

import com.musinsa.homework.util.CustomSpringELParser
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint
import org.springframework.stereotype.Component

/**
 * `@RedisDistributedLock` 어노테이션 선언시 사용하는 AOP 객체입니다.
 *
 * @see <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">코드 작성시 참고한 컬리 기술블로그 글</a>
 */
@Aspect
@Component
class RedisDistributedLockAop(
  private val redissonClient: RedissonClient,
  private val redisDistributedRockForTransaction: RedisDistributedRockForTransaction
) {
  private val log = LoggerFactory.getLogger(this.javaClass)!!

  companion object {
    private const val REDISSON_LOCK_PREFIX = "LOCK:"
  }

  /**
   * 레디스를 활용한 분산락 기능을 활성화합니다.
   */
  @Around("@annotation(com.musinsa.homework.util.lock.distributed.RedisDistributedLock)")
  fun lock(joinPoint: MethodInvocationProceedingJoinPoint): Any {

    val signature = joinPoint.signature as MethodSignature
    val method = signature.method
    val redisDistributedLock = method.getAnnotation(RedisDistributedLock::class.java)
    val key = REDISSON_LOCK_PREFIX + CustomSpringELParser.getDynamicValue(
      signature.parameterNames,
      joinPoint.args,
      redisDistributedLock.key
    )
    val readOnly = redisDistributedLock.readOnly
    val rLock = redissonClient.getLock(key)

    val available =
      rLock.tryLock(redisDistributedLock.waitTime, redisDistributedLock.leaseTime, redisDistributedLock.timeUnit)

    if (!available) {
      return false
    }

    return try {
      if (readOnly) redisDistributedRockForTransaction.readOnlyProceed(joinPoint).apply { rLock.unlock() }
      else redisDistributedRockForTransaction.notReadOnlyProceed(joinPoint).apply { rLock.unlock() }
    } catch (e: IllegalMonitorStateException) {
      log.info("이미 해제된 분산락입니다. (메소드명 : ${method.name}, 분산락 키: $key)")
    }
  }
}
