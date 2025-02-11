package com.musinsa.homework.jpa.entities.category

import org.springframework.data.jpa.repository.JpaRepository

interface CategoryRepository : JpaRepository<Category, String> {
}
