package com.teste.renner.application.data.mapper

import com.teste.renner.application.data.mapper.CategoryMapper.toCategory
import com.teste.renner.application.data.mapper.CategoryMapper.toCategoryList
import com.teste.renner.application.data.response.CategoryResponse
import com.teste.renner.application.domain.model.Category
import org.assertj.core.api.Assertions
import org.junit.Test

class CategoryMapperTest {
    @Test
    fun `List CategoryResponse must map to toCategoryList`() {
        val mapperTest = CategoryResponse.mock().toCategoryList()
        Assertions.assertThat(mapperTest.toString()).isEqualTo(Category.mock().toString())
    }

    @Test
    fun `CategoryResponse must map to toCategory`() {
        val mapperTest = CategoryResponse.mock().first().toCategory()
        Assertions.assertThat(mapperTest.toString()).isEqualTo(Category.mock().first().toString())
    }
}