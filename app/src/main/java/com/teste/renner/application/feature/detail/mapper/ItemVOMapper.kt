package com.teste.renner.application.feature.detail.mapper

import com.teste.renner.application.domain.model.Product
import com.teste.renner.application.feature.detail.viewobject.ItemVO

object ItemVOMapper {
    fun Product.toProduct() = ItemVO(
        id = id,
        image = image,
        name = name,
        price = price,
        brand = brand,
        type = type,
        color = color,
        size = size,
    )
}