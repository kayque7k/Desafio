package com.teste.poc.application.domain.repository

import com.teste.poc.application.domain.model.Product

interface ProductRepository {

    suspend fun getProducts(idCategory: Int): List<Product>

    suspend fun getProduct(id: Int): Product
}