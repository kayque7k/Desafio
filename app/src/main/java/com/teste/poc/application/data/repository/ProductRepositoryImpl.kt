package com.teste.poc.application.data.repository

import com.teste.poc.application.data.api.ProductApi
import com.teste.poc.application.data.mapper.ProductMapper.toProduct
import com.teste.poc.application.data.mapper.ProductMapper.toProductList
import com.teste.poc.application.domain.model.Product
import com.teste.poc.application.domain.repository.ProductRepository

class ProductRepositoryImpl(
    val productApi: ProductApi
) : ProductRepository {

    override suspend fun getProduct(id: Int): Product =
        productApi.getProduct(id = id).toProduct()

    override suspend fun getProducts(idCategory: Int): List<Product> =
        productApi.getProducts(idCategory = idCategory).toProductList()
}