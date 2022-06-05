package com.teste.poc.application.data.mapper

import com.teste.poc.application.data.response.CategoryResponse
import com.teste.poc.application.domain.model.Category

object CategoryMapper {
    fun List<CategoryResponse>.toCategoryList() = map {
        it.toCategory()
    }

    fun CategoryResponse.toCategory() = Category(
        id = id,
        image = image,
        category = category
    )
}