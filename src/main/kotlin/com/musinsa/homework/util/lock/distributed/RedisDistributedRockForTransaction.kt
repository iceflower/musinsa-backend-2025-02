package com.musinsa.homework.util.lock.distributed

import org.aspectj.lang.ProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

/**
 * `@RedisDistributedLock` 어노테이션 선언 시 사용하는 트랜잭션 객체입니다.
 *
 * @see <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">코드 작성시 참고한 컬리 기술블로그 글</a>
 */
@Component
class RedisDistributedRockForTransaction {

  /**
   * readonly 가 아닌 트랜잭션을 확성화시켜주는 메소드입니다.
   *
   * `Propagation.REQUIRES_NEW` 를 사용한 이유는 다음과 같습니다.
   *  - `REQUIRES_NEW` 를 사용하지 않으면, 분산락이 비즈니스 로직 트랜잭션 커밋 이전에 해제될 수 있으며,
   *  이 경우 비즈니스 로직 트랜잭션이 커밋되기 이전에 다른 스레드에서 락을 얻어 비즈니스 로직을 수행할 가능성이 있습니다. <br>
   *  - 이로 인해 정합성을 확보하기 어려우므로, 트랜잭션 커밋이 무조건 락 해제 이전에 진행될 수 있도록 하기 위해 사용하게 되었습니다.
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  fun notReadOnlyProceed(joinPoint: ProceedingJoinPoint): Any = joinPoint.proceed()

  /**
   * readonly인 트랜잭션을 확성화시켜주는 메소드입니다.
   *
   * `Propagation.REQUIRES_NEW` 를 사용한 이유는 다음과 같습니다.
   *  - `REQUIRES_NEW` 를 사용하지 않으면, 분산락이 비즈니스 로직 트랜잭션 커밋 이전에 해제될 수 있으며,
   *  이 경우 비즈니스 로직 트랜잭션이 커밋되기 이전에 다른 스레드에서 락을 얻어 비즈니스 로직을 수행할 가능성이 있습니다. <br>
   *  - 이로 인해 정합성을 확보하기 어려우므로, 트랜잭션 커밋이 무조건 락 해제 이전에 진행될 수 있도록 하기 위해 사용하게 되었습니다.
   */
  @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = true)
  fun readOnlyProceed(joinPoint: ProceedingJoinPoint): Any = joinPoint.proceed()
}
