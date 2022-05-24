package com.teste.renner.dsc.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberImagePainter
import com.teste.renner.dsc.color.ColorPalette
import com.teste.renner.dsc.dimen.Size

@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    imageUrl: String,
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
        painter = rememberImagePainter(
            data = imageUrl
        ),
        contentDescription = contentDescription
    )
}