package com.teste.poc.application.feature.menu.mapper

import com.teste.poc.application.domain.model.Category
import com.teste.poc.application.feature.menu.viewobject.MenuVO

object MenuVOMapper {
    fun List<Category>.toCategoryList() = map {
        it.toCategory()
    }

    fun Category.toCategory() = MenuVO(
        id = id,
        image = image,
        category = category
    )
}