package com.teste.poc.application.domain.model

import com.teste.poc.commons.extensions.EMPTY_STRING
import com.teste.poc.commons.extensions.ZERO

data class Category(
    val id: Int = ZERO,
    val image: String = EMPTY_STRING,
    val category: String = EMPTY_STRING
){
    companion object {
        fun mock() = listOf(
            Category(
                id = 1,
                image = "https://blog.connectplug.com.br/wp-content/uploads/2018/01/como-decorar-uma-loja-de-roupas-e1522869386918.jpg",
                category = "Roupas"
            ),
            Category(
                id = 2,
                image = "https://www.showmetech.com.br/wp-content/uploads//2021/05/melhorescelulares-1920x1024.jpg",
                category = "Smartphone"
            ),
            Category(
                id = 3,
                image = "https://jpimg.com.br/uploads/2017/04/1114044144-carro-em-exposicao-no-salao-do-automovel-1024x683.jpg",
                category = "Automóvel"
            ),
            Category(
                id = 4,
                image = "https://capitalaberto.com.br/wp-content/uploads/2021/10/18.10_dest_Reportagem-moda.jpg",
                category = "Moda"
            ),
            Category(
                id = 5,
                image = "https://www.premiumplanejados.com.br/wp-content/uploads/2020/12/Qual-a-durabilidade-de-moveis-planejados-1200x675.jpg",
                category = "Moveis"
            )
        )
    }
}
