package com.teste.renner.application.feature.detail.viewobject

import com.teste.renner.commons.extensions.EMPTY_STRING
import com.teste.renner.commons.extensions.ZERO

class ItemVO(
    var id: Int = ZERO,
    var price: String = EMPTY_STRING,
    var image: String = EMPTY_STRING,
    var name: String = EMPTY_STRING,
    var color: String = EMPTY_STRING,
    var size: String = EMPTY_STRING,
    var type: String = EMPTY_STRING,
    var brand: String = EMPTY_STRING
)