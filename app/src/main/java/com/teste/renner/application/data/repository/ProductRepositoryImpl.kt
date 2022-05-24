package com.teste.renner.application.data.repository

import com.teste.renner.application.data.api.ProductApi
import com.teste.renner.application.data.mapper.ProductMapper.toProduct
import com.teste.renner.application.data.mapper.ProductMapper.toProductList
import com.teste.renner.application.data.mockapi.ProductApiMock
import com.teste.renner.application.domain.model.Product
import com.teste.renner.application.domain.repository.ProductRepository

class ProductRepositoryImpl(
    val productApi: ProductApi
) : ProductRepository {

    override suspend fun getProduct(id: Int): Product =
        productApi.getProduct(id = id).toProduct()

    override suspend fun getProducts(idCategory: Int): List<Product> =
        productApi.getProducts(idCategory = idCategory).toProductList()
}