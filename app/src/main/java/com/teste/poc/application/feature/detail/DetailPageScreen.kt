package com.teste.poc.application.feature.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.IconButton
import androidx.compose.material.Icon
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.teste.poc.application.feature.main.MainActivity
import com.teste.poc.application.feature.main.MainViewModel
import com.teste.poc.dsc.dimen.Size
import com.teste.poc.application.feature.detail.DetailViewModel.UiState
import com.teste.poc.commons.extensions.MAX_LINE_DEFAULT
import com.teste.poc.dsc.color.ColorPalette
import com.teste.poc.dsc.component.ImageLoader
import com.teste.poc.dsc.component.SpacerVertical
import com.teste.poc.dsc.dimen.Font
import com.teste.poc.dsc.dimen.Radius
import com.teste.poc.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter
import com.teste.poc.dsc.dimen.Weight

@Composable
fun DetailPageScreen(
    viewModel: DetailViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.getDetails(
            activity = activity,
            id = flowViewModel.idLover
        )
    }

    Screen(
        uiState = viewModel.uiState,
        onClickMusic = {
            viewModel.onClickMusic(activity = activity, url = it)
        },
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
    onClickMusic: (url: String) -> Unit,
    onClickClose: () -> Unit
) = MaterialTheme {
    if (uiState.item.collectAsState().value.imageBackground.isNotEmpty()) {
        Image(
            painter = rememberAsyncImagePainter(
                uiState.item.collectAsState().value.imageBackground
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Transparent,
    ) {
        val screenState by uiState.screenState.collectAsState()
        when (screenState) {
            is DetailViewModel.ScreenState.ScreenLoading -> ScreenProgress()
            is DetailViewModel.ScreenState.ScreenContent -> ScreenContent(
                uiState = uiState,
                onClickMusic = onClickMusic,
                onClickClose = onClickClose
            )
            is DetailViewModel.ScreenState.ScreenError -> {}
        }
    }
}

@Composable
fun ScreenContent(
    uiState: UiState,
    onClickMusic: (url: String) -> Unit,
    onClickClose: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize()
) {
    if (uiState.item.collectAsState().value.cardsVO.isNotEmpty()) {
        Content(uiState = uiState)
    }
    Header(
        uiState = uiState,
        onClickMusic = onClickMusic,
        onClickClose = onClickClose
    )
}

@Composable
fun Header(
    uiState: UiState,
    onClickMusic: (url: String) -> Unit,
    onClickClose: () -> Unit,
) = Column(
    modifier = Modifier
        .padding(
            top = Size.Size4,
            bottom = Size.Size4,
            start = Size.Size16,
            end = Size.Size16,
        )
        .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Row {
        IconButton(
            modifier = Modifier
                .padding(
                    top = Size.Size32
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(Weight.Weight_1)
        )
        if (uiState.item.value.cardsVO.firstOrNull()?.music.orEmpty().isNotEmpty()) {
            IconButton(
                modifier = Modifier
                    .padding(
                        top = Size.Size32,
                        start = Size.Size4
                    ),
                onClick = {
                    onClickMusic.invoke(
                        uiState.item.value.cardsVO.firstOrNull()?.music.orEmpty()
                    )
                }
            ) {
                Icon(
                    modifier = Modifier.size(size = Size.Size32),
                    imageVector = Icons.Rounded.PlayArrow,
                    tint = ColorPalette.PlayMusic,
                    contentDescription = stringResource(
                        id = R.string.accessibily_details_play
                    )
                )
            }
        }
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
                image = uiState.item.collectAsState().value.cardsVO.firstOrNull()?.image.orEmpty(),
                contentDescription = uiState.item.collectAsState().value.cardsVO.firstOrNull()?.name.orEmpty()
            )
            SpacerVertical()
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = uiState.item.collectAsState().value.cardsVO.firstOrNull()?.name.orEmpty(),
                fontWeight = FontWeight.SemiBold,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis,
                maxLines = MAX_LINE_DEFAULT
            )
            SpacerVertical(dp = Size.Size16)
            Text(
                modifier = Modifier.padding(
                    horizontal = Size.Size8
                ),
                text = uiState.item.collectAsState().value.cardsVO.firstOrNull()?.description.orEmpty(),
                fontWeight = FontWeight.Normal,
                fontSize = Font.Font20,
                fontFamily = FontFamily.SansSerif
            )
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
