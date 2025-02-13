package com.musinsa.homework.jpa.entities.category

import com.musinsa.homework.jpa.entities.category.vo.PriceByCategory
import org.springframework.jdbc.core.JdbcTemplate


class CategoryRepositoryImpl(
  private val jdbcTemplate: JdbcTemplate,
) : CategoryRepositoryCustom {

  override fun lowestPriceByAllCategories(): List<PriceByCategory> {
    return jdbcTemplate.query(
      """
      SELECT 
          c.category_name,
          b.brand_name,
          sub.min_price
      FROM product p
      JOIN category c ON c.category_id = p.category_id
      JOIN brand b ON b.brand_id = p.brand_id
      JOIN (
          SELECT
              category_id, 
              MIN(product_price) AS min_price
          FROM product
          GROUP BY category_id
      ) sub ON p.category_id = sub.category_id AND p.product_price = sub.min_price
      
      ORDER BY p.category_id
    """.trimIndent()
    ) { rs, _ ->
      PriceByCategory(
        rs.getString("category_name"),
        rs.getString("brand_name"),
        rs.getLong("min_price")
      )
    }
  }

  override fun lowestPriceByEachCategory(name: String): PriceByCategory? {
    return jdbcTemplate.queryForObject(
      """
        SELECT 
            c.category_name,
            b.brand_name,
            sub.min_price
        FROM product p
        JOIN category c ON c.category_id = p.category_id
        JOIN brand b ON b.brand_id = p.brand_id
        JOIN (
            SELECT
                category_id, 
                MIN(product_price) AS min_price
            FROM product
            GROUP BY category_id
        ) sub ON p.category_id = sub.category_id AND p.product_price = sub.min_price
        
        WHERE c.category_name = '$name'
    """.trimIndent()
    ) { rs, _ ->
      PriceByCategory(
        rs.getString("category_name"),
        rs.getString("brand_name"),
        rs.getLong("min_price")
      )
    }
  }

  override fun highestPriceByEachCategory(name: String): PriceByCategory? {
    return jdbcTemplate.queryForObject(
      """
      SELECT 
          c.category_name,
          b.brand_name,
          sub.max_price
      FROM product p
      JOIN category c ON c.category_id = p.category_id
      JOIN brand b ON b.brand_id = p.brand_id
      JOIN (
          SELECT
              category_id, 
              MAX(product_price) AS max_price
          FROM product
          GROUP BY category_id
      ) sub ON p.category_id = sub.category_id AND p.product_price = sub.max_price
      
      WHERE c.category_name = '$name'
    """.trimIndent()
    ) { rs, _ ->
      PriceByCategory(
        rs.getString("category_name"),
        rs.getString("brand_name"),
        rs.getLong("max_price")
      )
    }
  }
}
