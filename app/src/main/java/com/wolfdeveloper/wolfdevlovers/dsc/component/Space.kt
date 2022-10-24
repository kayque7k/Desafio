package com.wolfdeveloper.wolfdevlovers.dsc.component

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size

@Composable
fun SpacerVertical(dp: Dp = Size.Size16) {
    Spacer(modifier = Modifier.height(dp))
}

@Composable
fun SpacerHorizontal(dp: Dp = Size.Size16) {
    Spacer(modifier = Modifier.width(dp))
}
