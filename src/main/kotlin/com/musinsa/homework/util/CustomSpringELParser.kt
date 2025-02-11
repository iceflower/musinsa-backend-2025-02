package com.musinsa.homework.util

import org.springframework.expression.ExpressionParser
import org.springframework.expression.spel.standard.SpelExpressionParser
import org.springframework.expression.spel.support.StandardEvaluationContext

/**
 * SpringEL을 사용하기 위한 커스터마이징된 파서 객체.
 *
 * @see <a href="https://helloworld.kurly.com/blog/distributed-redisson-lock/">코드 작성시 참고한 컬리 기술블로그 글</a>
 */
class CustomSpringELParser {

  companion object {
    fun getDynamicValue(parameterNames: Array<String>, args: Array<Any>, key: String): Any {
      val parser: ExpressionParser = SpelExpressionParser()
      val context = StandardEvaluationContext()

      for (i in parameterNames.indices) {
        context.setVariable(parameterNames[i], args[i])
      }

      return parser.parseExpression(key)
        .getValue(context, Any::class)!!
    }
  }
}
