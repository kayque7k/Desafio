package com.teste.renner.application.data.mapper

import com.teste.renner.application.data.response.ProductResponse
import com.teste.renner.application.domain.model.Product

object ProductMapper {
    fun List<ProductResponse>.toProductList() = map {
        it.toProduct()
    }

    fun ProductResponse.toProduct() = Product(
        id = id,
        name = name,
        image = image,
        price = price,
        color = color,
        size = size,
        type = type,
        brand = brand,
        idCategory = idCategory
    )
}