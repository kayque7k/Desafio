package com.teste.renner.application.feature.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import com.teste.renner.application.feature.main.MainActivity
import com.teste.renner.application.feature.main.MainViewModel
import com.teste.renner.dsc.dimen.Size
import com.teste.renner.application.feature.detail.DetailViewModel.UiState
import com.teste.renner.commons.extensions.MAX_LINE_DEFAULT
import com.teste.renner.dsc.color.ColorPalette
import com.teste.renner.dsc.component.ImageLoader
import com.teste.renner.dsc.component.SpacerVertical
import com.teste.renner.dsc.dimen.Font
import com.teste.renner.dsc.dimen.Radius
import com.teste.renner.R
import androidx.compose.material.icons.rounded.ArrowBack
import com.teste.renner.commons.extensions.SPACE_STRING

const val MAX_LINE_DESCRIPTION = 3
const val RADIUS_PERCENT = 25

@Composable
fun DetailPageScreen(
    viewModel: DetailViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.getDetails(
            id = flowViewModel.idProduct
        )
    }

    Screen(
        uiState = viewModel.uiState,
        onClickClose = viewModel::onClickBack
    )

    EventConsumer(
        activity = activity,
        viewModel = viewModel,
        flowviewModel = flowViewModel
    )
}

@Composable
private fun Screen(
    uiState: UiState,
    onClickClose: () -> Unit
) = MaterialTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ColorPalette.SteelGrey,
    ) {
        val screenState by uiState.screenState.collectAsState()
        when (screenState) {
            is DetailViewModel.ScreenState.ScreenLoading -> ScreenProgress()
            is DetailViewModel.ScreenState.ScreenContent -> ScreenContent(
                uiState = uiState,
                onClickClose = onClickClose
            )
            is DetailViewModel.ScreenState.ScreenError -> ScreenError()
        }
    }
}

@Composable
fun ScreenContent(
    uiState: UiState,
    onClickClose: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize()
) {
    Content(uiState = uiState)
    IconButton(
        modifier = Modifier
            .padding(
                top = Size.Size32,
                start = Size.Size4
            ),
        onClick = onClickClose
    ) {
        Icon(
            modifier = Modifier
                .size(size = Size.Size32),
            imageVector = Icons.Rounded.ArrowBack,
            contentDescription = stringResource(id = R.string.accessibily_details_back)
        )
    }
}

@Composable
fun Content(
    uiState: UiState
) = Column(
    modifier = Modifier.verticalScroll(rememberScrollState())
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(Radius.Radius8),
        backgroundColor = ColorPalette.White
    ) {
        Column {
            ImageLoader(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = Size.Size450),
                imageUrl = uiState.item.collectAsState().value.image,
                contentDescription = uiState.item.collectAsState().value.name
            )
            SpacerVertical()
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = stringResource(
                    id = R.string.dollar,
                    uiState.item.collectAsState().value.price
                ),
                fontWeight = FontWeight.Normal,
                fontSize = Font.Font25,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DEFAULT
            )
            SpacerVertical()
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.details_name) + SPACE_STRING)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(uiState.item.collectAsState().value.name)
                    }
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DEFAULT
            )
            SpacerVertical(dp = Size.Size8)
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.details_description) + SPACE_STRING)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(uiState.item.collectAsState().value.size)
                    }
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DESCRIPTION
            )
            SpacerVertical(dp = Size.Size8)
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.details_type) + SPACE_STRING)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(uiState.item.collectAsState().value.type)
                    }
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DEFAULT
            )
            SpacerVertical(dp = Size.Size8)
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.details_brand) + SPACE_STRING)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(uiState.item.collectAsState().value.brand)
                    }
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DEFAULT
            )
            SpacerVertical(dp = Size.Size8)
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(stringResource(id = R.string.details_color) + SPACE_STRING)
                    }
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                        append(uiState.item.collectAsState().value.color)
                    }
                },
                fontWeight = FontWeight.SemiBold,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DEFAULT
            )
            SpacerVertical(dp = Size.Size8)
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = Size.Size128
                        ),
                    colors = ButtonDefaults.buttonColors(backgroundColor = ColorPalette.SteelGrey),
                    shape = RoundedCornerShape(RADIUS_PERCENT),
                    onClick = {}
                ) {
                    Text(
                        modifier = Modifier.padding(
                            vertical = Size.Size4,
                            horizontal = Size.Size16
                        ),
                        text = stringResource(id = R.string.details_button),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = Font.Font20,
                        fontFamily = FontFamily.SansSerif,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        color = ColorPalette.White,
                        maxLines = MAX_LINE_DEFAULT
                    )
                }
            }
            SpacerVertical(dp = Size.Size16)
        }
    }
}

@Composable
private fun ScreenProgress() = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Size.SizeProgress),
            color = ColorPalette.Black
        )
    }
}

@Composable
private fun ScreenError() = Column(
    modifier = Modifier
        .fillMaxSize()
) {
    Text(
        modifier = Modifier.padding(
            all = Size.Size64,
        ),
        text = stringResource(id = R.string.not_search),
        color = ColorPalette.White,
        fontSize = Font.Font16,
        textAlign = TextAlign.Center
    )
}


@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: DetailViewModel,
    flowviewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                DetailViewModel.ScreenEvent.GoBack -> activity.onBackPressed()
                is DetailViewModel.ScreenEvent.NavigateTo -> flowviewModel.navigate(event.navigation)
            }
        }
    }
}
