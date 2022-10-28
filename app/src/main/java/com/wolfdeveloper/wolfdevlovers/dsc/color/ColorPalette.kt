package com.wolfdeveloper.wolfdevlovers.dsc.color

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.wolfdeveloper.wolfdevlovers.R

object ColorPalette {
    val System @Composable get() = colorResource(id = R.color.system)
    val White @Composable get() = colorResource(id = android.R.color.white)
    val SteelGrey @Composable get() = colorResource(id = R.color.steel_grey)
    val CharcoalGrey @Composable get() = colorResource(id = R.color.charcoal_grey)
    val Black @Composable get() = colorResource(id = R.color.black)
    val PlayMusic @Composable get() = colorResource(id = R.color.play_music)
}