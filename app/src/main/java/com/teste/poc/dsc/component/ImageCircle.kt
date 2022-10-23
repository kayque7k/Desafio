package com.teste.poc.dsc.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.rememberAsyncImagePainter
import com.teste.poc.dsc.dimen.Size

@Composable
fun ImageCircle(
    url: String,
    contentDescription: String
) = Image(
    modifier = Modifier
        .size(Size.SizeProfile)
        .clip(CircleShape),
    painter = rememberAsyncImagePainter(url),
    contentDescription = contentDescription
)
