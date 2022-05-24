package com.teste.renner.application.domain.repository

import com.teste.renner.application.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(idCategory: Int): List<Product>

    suspend fun getProduct(id: Int): Product
}