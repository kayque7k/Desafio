package com.teste.poc.dsc.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.teste.poc.dsc.color.ColorPalette
import com.teste.poc.dsc.dimen.Size

@Composable
fun ImageCircle(
    modifier: Modifier = Modifier,
    imageUrl: String,
    contentDescription: String,
    backgroundColor: Color = ColorPalette.White
) {
    Box(
        modifier = modifier.background(
            color = backgroundColor,
            shape = CircleShape
        ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(Size.Size100),
            painter = rememberImagePainter(
                data = imageUrl,
                builder = { transformations(CircleCropTransformation()) }
            ),
            contentDescription = contentDescription
        )
    }
}