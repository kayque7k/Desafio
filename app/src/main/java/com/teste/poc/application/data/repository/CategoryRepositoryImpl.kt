package com.teste.poc.application.data.repository

import com.teste.poc.application.data.api.CategoryApi
import com.teste.poc.application.data.mapper.CategoryMapper.toCategoryList
import com.teste.poc.application.domain.model.Category
import com.teste.poc.application.domain.repository.CategoryRepository

class CategoryRepositoryImpl(
    val categoryApi: CategoryApi
) : CategoryRepository {

    override suspend fun getCategorys(): List<Category> =
        categoryApi.getCategorys().toCategoryList()
}