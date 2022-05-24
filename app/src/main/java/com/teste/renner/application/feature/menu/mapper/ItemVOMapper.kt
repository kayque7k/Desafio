package com.teste.renner.application.feature.menu.mapper

import com.teste.renner.application.domain.model.Product
import com.teste.renner.application.feature.menu.viewobject.ItemVO

object ItemVOMapper {
    fun List<Product>.toProductList() = map {
        it.toProduct()
    }

    fun Product.toProduct() = ItemVO(
        id = id,
        image = image,
        name = name,
        price = price,
        description = "$brand \\ $type \\ $color"
    )
}