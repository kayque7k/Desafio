package com.teste.renner.application.feature.menu

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Surface
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.teste.renner.R
import com.teste.renner.application.feature.main.MainActivity
import com.teste.renner.application.feature.main.MainViewModel
import com.teste.renner.dsc.dimen.Size
import com.teste.renner.application.feature.menu.MenuViewModel.ScreenEvent
import com.teste.renner.application.feature.menu.MenuViewModel.UiState
import com.teste.renner.commons.extensions.MAX_LINE_DEFAULT
import com.teste.renner.dsc.color.ColorPalette
import com.teste.renner.dsc.component.ImageCircle
import com.teste.renner.dsc.component.ImageLoader
import com.teste.renner.dsc.component.SpacerVertical
import com.teste.renner.dsc.component.rippleClickable
import com.teste.renner.dsc.dimen.Font
import com.teste.renner.dsc.dimen.Radius
import com.teste.renner.dsc.dimen.Size.Size350

@Composable
fun MenuPageScreen(
    viewModel: MenuViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.setup()
    }

    Screen(
        uiState = viewModel.uiState,
        onClickItem = {
            flowViewModel.setIdProduct(it)
            viewModel.onClickItem()
        },
        onClickItemCategory = viewModel::onClickItemCategory
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
    onClickItem: (Int) -> Unit,
    onClickItemCategory: (Int) -> Unit,
) = MaterialTheme {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = ColorPalette.SteelGrey,
    ) {
        val screenState by uiState.screenState.collectAsState()
        when (screenState) {
            is MenuViewModel.ScreenState.ScreenLoading -> ScreenProgress()
            is MenuViewModel.ScreenState.ScreenContent -> ScreenContent(
                uiState = uiState,
                onClickItem = onClickItem,
                onClickItemCategory = onClickItemCategory
            )
            is MenuViewModel.ScreenState.ScreenError -> ScreenError()
        }
    }
}

@Composable
fun ScreenContent(
    uiState: UiState,
    onClickItem: (Int) -> Unit,
    onClickItemCategory: (Int) -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
) {
    SpacerVertical(dp = Size.Size64)
    Header()
    SpacerVertical(dp = Size.Size32)
    TitleProduct()
    SpacerVertical()
    when (uiState.loadingProduct.collectAsState().value) {
        true -> ScreenProgress()
        false -> ListItem(
            uiState = uiState,
            onClickItem = onClickItem
        )
    }
    SpacerVertical(dp = Size.Size32)
    TitleCategory()
    SpacerVertical(dp = Size.Size16)
    ListMenu(
        uiState = uiState,
        onClickItemCategory = onClickItemCategory
    )
    SpacerVertical(dp = Size.Size8)
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = stringResource(id = R.string.scroll),
        color = ColorPalette.CharcoalGrey,
        fontWeight = FontWeight.SemiBold,
        fontFamily = FontFamily.SansSerif,
        fontSize = Font.Font16,
        textAlign = TextAlign.Center
    )
    SpacerVertical()

}

@Composable
fun Header() = Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Text(
        text = stringResource(id = R.string.logo),
        fontSize = Font.Font30,
        color = ColorPalette.White,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
    )
}

@Composable
fun TitleProduct() = Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Text(
        modifier = Modifier.padding(
            horizontal = Size.Size8
        ),
        text = stringResource(id = R.string.title_products),
        fontWeight = FontWeight.Bold,
        fontSize = Font.Font25,
        fontFamily = FontFamily.SansSerif,
        color = ColorPalette.White
    )
}

@Composable
fun TitleCategory() = Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Text(
        modifier = Modifier.padding(
            horizontal = Size.Size8
        ),
        text = stringResource(id = R.string.title_category),
        fontWeight = FontWeight.Bold,
        fontSize = Font.Font25,
        fontFamily = FontFamily.SansSerif,
        color = ColorPalette.White
    )
}

@Composable
fun ListItem(
    uiState: UiState,
    onClickItem: (Int) -> Unit
) = Row(
    modifier = Modifier.horizontalScroll(rememberScrollState())
) {
    uiState.listItens.collectAsState().value.forEach {
        Column(
            modifier = Modifier.padding(
                horizontal = Size.Size4
            )
        ) {
            Card(
                modifier = Modifier
                    .width(width = Size350)
                    .padding(
                        start = Size.Size16,
                        end = Size.Size16
                    )
                    .rippleClickable { onClickItem(it.id) },
                shape = RoundedCornerShape(Radius.Radius8),
                backgroundColor = ColorPalette.White
            ) {
                Column {
                    ImageLoader(
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(size = Size350),
                        imageUrl = it.image,
                        contentDescription = it.name
                    )
                    SpacerVertical()
                    Text(
                        modifier = Modifier.padding(
                            horizontal = Size.Size8
                        ),
                        text = stringResource(id = R.string.dollar, it.price),
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
                        text = it.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = Font.Font20,
                        fontFamily = FontFamily.SansSerif,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = MAX_LINE_DEFAULT
                    )
                    SpacerVertical(dp = Size.Size4)
                    Text(
                        modifier = Modifier.padding(
                            horizontal = Size.Size8
                        ),
                        text = it.description,
                        fontWeight = FontWeight.Normal,
                        fontSize = Font.Font20,
                        fontFamily = FontFamily.SansSerif,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = MAX_LINE_DEFAULT
                    )
                    SpacerVertical(dp = Size.Size8)
                }
            }
        }
    }
}

@Composable
fun ListMenu(
    uiState: UiState,
    onClickItemCategory: (Int) -> Unit
) = Row(
    modifier = Modifier.horizontalScroll(rememberScrollState())
) {
    uiState.listMenu.collectAsState().value.forEach {
        Column(
            modifier = Modifier
                .padding(horizontal = Size.Size16)
                .rippleClickable {
                    onClickItemCategory(it.id)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ImageCircle(
                imageUrl = it.image,
                contentDescription = it.category
            )
            SpacerVertical()
            Text(
                modifier = Modifier
                    .padding(bottom = Size.Size4),
                text = it.category,
                fontWeight = FontWeight.Bold,
                fontSize = Font.Font20,
                textAlign = TextAlign.Center,
                color = ColorPalette.White,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Composable
private fun ScreenError() = Column(
    modifier = Modifier.fillMaxSize()
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
private fun ScreenProgress() = Column(
    modifier = Modifier
        .fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(Size.SizeProgress),
            color = ColorPalette.White
        )
    }
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: MenuViewModel,
    flowviewModel: MainViewModel
) {
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventsFlow.collect { event ->
            when (event) {
                ScreenEvent.GoBack -> activity.onBackPressed()
                is ScreenEvent.NavigateTo -> flowviewModel.navigate(event.navigation)
            }
        }
    }
}
