package com.teste.renner.application.domain.repository

import com.teste.renner.application.domain.model.Category

interface CategoryRepository {

    suspend fun getCategorys(
    ): List<Category>
}