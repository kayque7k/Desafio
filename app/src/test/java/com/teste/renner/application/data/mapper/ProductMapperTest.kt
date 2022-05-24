package com.teste.renner.application.data.mapper

import com.teste.renner.application.data.mapper.ProductMapper.toProduct
import com.teste.renner.application.data.mapper.ProductMapper.toProductList
import com.teste.renner.application.data.response.ProductResponse
import com.teste.renner.application.domain.model.Product
import io.mockk.InternalPlatformDsl.toStr
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