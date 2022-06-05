package com.teste.poc.application.data.mapper

import com.teste.poc.application.data.mapper.ProductMapper.toProduct
import com.teste.poc.application.data.mapper.ProductMapper.toProductList
import com.teste.poc.application.data.response.ProductResponse
import com.teste.poc.application.domain.model.Product
import org.assertj.core.api.Assertions
import org.junit.Test

class ProductMapperTest {
    @Test
    fun `List ProductResponse must map to toProductList`() {
        val mapperTest = ProductResponse.mock().toProductList()
        Assertions.assertThat(mapperTest.first().name).isEqualTo(Product.mock().first().name)
    }

    @Test
    fun `toProductList must map to toProduct`() {
        val mapperTest = ProductResponse.mock().first().toProduct()
        Assertions.assertThat(mapperTest.name).isEqualTo(Product.mock().first().name)
    }
}