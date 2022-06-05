package com.teste.poc.application.data.mapper

import com.teste.poc.application.data.response.ProductResponse
import com.teste.poc.application.domain.model.Product

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