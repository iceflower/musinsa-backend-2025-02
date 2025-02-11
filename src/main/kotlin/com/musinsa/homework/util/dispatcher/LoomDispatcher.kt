package com.musinsa.homework.util.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

/**
 * Java21부터 정식 기능으로 추가된 버추얼 스레드 기능을, 코루틴에서 사용하기 위한 코드입니다.
 *
 * @see <a href="https://jsonobject.tistory.com/631">코드 작성시 참고한 블로그 글</a>
 */
val Dispatchers.LOOM: CoroutineDispatcher
  get() = Executors.newVirtualThreadPerTaskExecutor().asCoroutineDispatcher()
