package com.teste.renner.application.feature.menu.mapper

import com.teste.renner.application.domain.model.Category
import com.teste.renner.application.feature.menu.viewobject.MenuVO

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