package com.musinsa.homework.jpa.entities.product

import org.springframework.data.jpa.repository.JpaRepository

interface ProductRepository : JpaRepository<Product, String>
