package com.teste.poc.dsc.dimen

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.Dp
import com.teste.poc.R

object Size {
    val SizeProgress: Dp @Composable get() = dimensionResource(id = R.dimen.size_progress)

    val Size2: Dp @Composable get() = dimensionResource(id = R.dimen.size_2)
    val Size4: Dp @Composable get() = dimensionResource(id = R.dimen.size_4)
    val Size8: Dp @Composable get() = dimensionResource(id = R.dimen.size_8)
    val Size16: Dp @Composable get() = dimensionResource(id = R.dimen.size_16)
    val Size32: Dp @Composable get() = dimensionResource(id = R.dimen.size_32)
    val Size64: Dp @Composable get() = dimensionResource(id = R.dimen.size_64)
    val Size100: Dp @Composable get() = dimensionResource(id = R.dimen.size_100)
    val Size128: Dp @Composable get() = dimensionResource(id = R.dimen.size_128)
    val Size256: Dp @Composable get() = dimensionResource(id = R.dimen.size_256)
    val Size350: Dp @Composable get() = dimensionResource(id = R.dimen.size_350)
    val Size450: Dp @Composable get() = dimensionResource(id = R.dimen.size_450)
}