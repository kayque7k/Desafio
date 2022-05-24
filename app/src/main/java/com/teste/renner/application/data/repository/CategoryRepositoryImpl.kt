package com.teste.renner.application.data.repository

import com.teste.renner.application.data.api.CategoryApi
import com.teste.renner.application.data.mapper.CategoryMapper.toCategoryList
import com.teste.renner.application.domain.model.Category
import com.teste.renner.application.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    val categoryApi: CategoryApi
) : CategoryRepository {

    override suspend fun getCategorys(): List<Category> =
        categoryApi.getCategorys().toCategoryList()
}