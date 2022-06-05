package com.teste.poc.application.data.response

import com.teste.poc.commons.extensions.EMPTY_STRING
import com.teste.poc.commons.extensions.ZERO

class ProductResponse(
    var id: Int = ZERO,
    var price: String = EMPTY_STRING,
    var image: String = EMPTY_STRING,
    var name: String = EMPTY_STRING,
    var color: String = EMPTY_STRING,
    var size: String = EMPTY_STRING,
    var type: String = EMPTY_STRING,
    var brand: String = EMPTY_STRING,
    var idCategory: Int = ZERO,
) {
    companion object {
        fun mock() = listOf(
//          clothes
            ProductResponse(
                id = 1,
                idCategory = 1,
                image = "https://static.netshoes.com.br/produtos/camisa-polo-lacoste-original-fit-masculina/06/D66-0138-006/D66-0138-006_zoom4.jpg?ts=1647863375&ims=544x",
                name = "Camiseta",
                price = "100,00",
                color = "Preto",
                size = "Tamanho M",
                type = "Polo",
                brand = "Lacoste"
            ),
            ProductResponse(
                id = 2,
                idCategory = 1,
                image = "https://cdn-images.farfetch-contents.com/17/59/38/31/17593831_a6b4d14e-4a12-4d5f-9945-6eca6e6d41fc_1000.jpg",
                name = "Camiseta",
                price = "120,00",
                color = "Preto",
                size = "Tamanho M",
                type = "Normal",
                brand = "Off-white"
            ),
            ProductResponse(
                id = 3,
                idCategory = 1,
                image = "https://imgcentauro-a.akamaihd.net/900x900/95004702/camiseta-nike-jordan-jumpman-masculina-img.jpg",
                name = "Camiseta",
                price = "150,00",
                color = "Preto",
                size = "Tamanho M",
                type = "Normal",
                brand = "Jordan"
            ),
            ProductResponse(
                id = 4,
                idCategory = 1,
                image = "https://m.media-amazon.com/images/I/41BauTo6uzL._AC_SX679_.jpg",
                name = "Blusa de frio",
                price = "90,00",
                color = "Preto",
                size = "Tamanho G",
                type = "Moletom",
                brand = "Jordan"
            ),

//          Smartphone
            ProductResponse(
                id = 5,
                idCategory = 2,
                image = "https://www.fastshop.com.br//wcsstore/FastShopCAS/imagens/_AE_Apple/AEMLVD3BZAAZL/AEMLVD3BZAAZL_PRD_447_1.jpg",
                name = "Iphone 13 pro",
                price = "7.000,00",
                color = "Azul-Sierra",
                size = "6.1 Polegadas",
                type = "Celular",
                brand = "Apple"
            ),
            ProductResponse(
                id = 6,
                idCategory = 2,
                image = "https://www.fastshop.com.br//wcsstore/FastShopCAS/imagens/_AE_Apple/AEMHDJ3BRABCO/V2/AEMHDJ3BRABCO_PRD_447_5.jpg",
                name = "Iphone 11",
                price = "4.000,00",
                color = "Branco / Vermelho / Verde",
                size = "6.1 Polegadas",
                type = "Celular",
                brand = "Apple"
            ),
            ProductResponse(
                id = 7,
                idCategory = 2,
                image = "https://images-americanas.b2w.io/produtos/01/00/img/2875227/4/2875227451_1SZ.jpg",
                name = "Galaxy S21",
                price = "3.000,00",
                color = "Violeta",
                size = "6.2 Polegadas",
                type = "Celular",
                brand = "Samsung"
            ),
            ProductResponse(
                id = 8,
                idCategory = 2,
                image = "https://a-static.mlcdn.com.br/618x463/smartphone-motorola-edge-20-lite-128gb-grafite-5g-6gb-ram-tela-67-cam-tripla-selfie-32mp/magazineluiza/231525100/e35dc4b1ed8b95faa86021bafb968d38.jpg",
                name = "Edige 20 Lite",
                price = "1.766,10",
                color = "Grafite",
                size = "6.7 Polegadas",
                type = "Celular",
                brand = "Motorola"
            ),

//          Automovel
            ProductResponse(
                id = 9,
                idCategory = 3,
                image = "https://cdn.motor1.com/images/mgl/rKoGA6/s1/mercedes-benz-c200-2022.jpg",
                name = "Mercedes-Benz",
                price = "254.766,10",
                color = "Azul escuro",
                size = "0 km",
                type = "Sedan",
                brand = "Mercedes"
            ),
            ProductResponse(
                id = 10,
                idCategory = 3,
                image = "https://revistafullpower.com.br/wp-content/uploads/2016/04/1_Lancer-1.jpg",
                name = "LANCER EVOLUTION",
                price = "157.546,00",
                color = "Preto",
                size = "0 km",
                type = "Sedan",
                brand = "Mitsubishi"
            ),
            ProductResponse(
                id = 11,
                idCategory = 3,
                image = "https://revistacarro.com.br/wp-content/uploads/2020/10/Hyundai-HB20-turbo-2021.jpg",
                name = "HB20S",
                price = "78.327,00",
                color = "Marrom",
                size = "0 km",
                type = "Sedan",
                brand = "Hyndai"
            ),
            ProductResponse(
                id = 12,
                idCategory = 3,
                image = "https://cdn.motor1.com/images/mgl/Zoqqz/s3/bmw-320i-m-sport-2021-teste-br.jpg",
                name = "BMW Série 3",
                price = "268.592,00",
                color = "Branco",
                size = "10 km",
                type = "Sedan",
                brand = "BMW"
            ),

//          fashion
            ProductResponse(
                id = 13,
                idCategory = 4,
                image = "https://cdn-images.farfetch-contents.com/12/56/25/66/12562566_11923829_1000.jpg",
                name = "Bolsa",
                price = "8.733,00",
                color = "Marrom",
                size = "Tamanho 23,5 cm largura x 19 cm altura x 8 cm profundidade",
                type = "Couro",
                brand = "Gucci"
            ),
            ProductResponse(
                id = 14,
                idCategory = 4,
                image = "https://www.prada.com/content/dam/pradabkg_products/1/1BC/1BC169/2AWLF063R/1BC169_2AWL_F063R_V_HOO_SLF.jpg",
                name = "Bolsa",
                price = "12.653,00",
                color = "Prata",
                size = "Tamanho 22 cm largura x 18,5 cm altura x 4,5 cm profundidade",
                type = "Tecido",
                brand = "Prada"
            ),

//          mobile
            ProductResponse(
                id = 15,
                idCategory = 5,
                image = "https://staticmobly.akamaized.net/r/2000x2000/p/Inspirare-Mesa-de-Jantar-Retangular-Esparta-Mel-120-cm-0235-701328-1.jpg",
                name = "Mesa de Jantar Retangular",
                price = "253,00",
                color = "Madeira",
                size = "Tamanho 120 cm",
                type = "Mesa",
                brand = "Mobly"
            ),
            ProductResponse(
                id = 16,
                idCategory = 5,
                image = "https://imaginemoveis.com.br/wp-content/uploads/CAMA-DE-CASAL-J%C3%9ALIA-REF-1200-1000x1000.jpg",
                name = "Cama de Casal Queen",
                price = "3.099,00",
                color = "Madeira",
                size = "Tamanho (A x L x P): 125cm x 166cm x 215cm",
                type = "Cama",
                brand = "Imagine"
            ),
            ProductResponse(
                id = 16,
                idCategory = 5,
                image = "https://m.media-amazon.com/images/I/61LCZWKXkaL._AC_SY450_.jpg",
                name = "Comoda Quarto Premium 8 Gavetas",
                price = "1.080,00",
                color = "Madeira",
                size = "Tamanho (comprimento/largura/altura/peso):  Volume 1: 1,405x0,455x0,116 - 36.60KG",
                type = "Comoda",
                brand = "LUKALIAM"
            ),
        )
    }
}