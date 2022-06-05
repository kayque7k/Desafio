package com.teste.poc.application.domain.repository

import com.teste.poc.application.domain.model.Category

interface CategoryRepository {

    suspend fun getCategorys(
    ): List<Category>
}