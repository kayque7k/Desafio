package com.wolfdeveloper.wolfdevlovers.dsc.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.wolfdeveloper.wolfdevlovers.dsc.color.ColorPalette
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    image: String,
    contentDescription: String,
    backgroundLoader: Color = ColorPalette.Black
) = Box {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(Size.SizeProgress),
                color = backgroundLoader
            )
        }
    }
    Image(
        modifier = modifier,
        contentScale = ContentScale.Crop,
        painter = rememberAsyncImagePainter(image),
        contentDescription = contentDescription
    )
}