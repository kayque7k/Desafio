package com.teste.renner.dsc.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.teste.renner.R

object ColorPalette {
    val White @Composable get() = colorResource(id = android.R.color.white)
    val SteelGrey @Composable get() = colorResource(id = R.color.steel_grey)
    val CharcoalGrey @Composable get() = colorResource(id = R.color.charcoal_grey)
    val Black @Composable get() = colorResource(id = R.color.black)
}