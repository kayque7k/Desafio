package com.wolfdeveloper.wolfdevlovers.dsc.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import coil.compose.rememberAsyncImagePainter
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size

@Composable
fun ImageCircle(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String
) = Image(
    modifier = modifier
        .size(Size.SizeProfile)
        .clip(CircleShape),
    painter = rememberAsyncImagePainter(url),
    contentDescription = contentDescription
)
