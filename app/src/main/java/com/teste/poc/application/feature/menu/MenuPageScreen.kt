package com.teste.poc.application.feature.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Card
import androidx.compose.material.AlertDialog
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Forum
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.github.theapache64.twyper.SwipedOutDirection
import com.github.theapache64.twyper.Twyper
import com.teste.poc.R
import com.teste.poc.application.feature.main.MainActivity
import com.teste.poc.application.feature.main.MainViewModel
import com.teste.poc.application.feature.menu.MenuViewModel.ScreenEvent
import com.teste.poc.application.feature.menu.MenuViewModel.UiState
import com.teste.poc.application.feature.viewobject.ItemCardVO
import com.teste.poc.commons.extensions.MAX_LINE_DEFAULT
import com.teste.poc.commons.extensions.MAX_LINE_THREE
import com.teste.poc.dsc.color.ColorPalette
import com.teste.poc.dsc.component.SpacerVertical
import com.teste.poc.dsc.component.ImageCircle
import com.teste.poc.dsc.component.SpacerHorizontal
import com.teste.poc.dsc.component.rippleClickable
import com.teste.poc.dsc.component.ImageLoader
import com.teste.poc.dsc.dimen.Font
import com.teste.poc.dsc.dimen.Size
import com.teste.poc.dsc.dimen.Weight


private const val STACKCOUNT = 10
private const val PADDING = 5f
private const val HEIGHT_BASE = 600
private const val HEIGHT_MIN = 1.8
private const val HEIGHT_MAX = 1.5

@Composable
fun MenuPageScreen(
    viewModel: MenuViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.getPerson()
    }

    Screen(
        uiState = viewModel.uiState,
        onClickItem = {
            flowViewModel.setIdLover(it)
            viewModel.onClickItem()
        },
        onEmpty = viewModel::onEmpty,
        onSwipeRightItem = viewModel::onSwipeRightItem,
        onSwipeLeftItem = viewModel::onSwipeLeftItem,
        onClickChat = { viewModel.onClickChat(activity = activity) },
        onClickSpotify = { viewModel.onClickSpotify(activity = activity) },
        onClickBlock = viewModel::onClickBlock,
        onRetry = {
            viewModel.getPerson()
        }
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
    onEmpty: () -> Unit,
    onSwipeRightItem: (ItemCardVO) -> Unit,
    onSwipeLeftItem: (ItemCardVO) -> Unit,
    onClickChat: () -> Unit,
    onClickSpotify: () -> Unit,
    onClickBlock: () -> Unit,
    onRetry: () -> Unit,
) = MaterialTheme {
    Box(modifier = Modifier.fillMaxSize()) {
        if (uiState.item.collectAsState().value.imageBackground.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    uiState.item.collectAsState().value.imageBackground,
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
                is MenuViewModel.ScreenState.ScreenLoading -> ScreenProgress()
                is MenuViewModel.ScreenState.ScreenContent -> ScreenContent(
                    uiState = uiState,
                    onClickItem = onClickItem,
                    onEmpty = onEmpty,
                    onSwipeRightItem = onSwipeRightItem,
                    onSwipeLeftItem = onSwipeLeftItem,
                    onClickChat = onClickChat,
                    onClickBlock = onClickBlock
                )
                is MenuViewModel.ScreenState.ScreenError -> {
                    onRetry.invoke()
                }
            }
        }

        if (uiState.openDialogFavorite.collectAsState().value)
            Dialog(onDismissRequest = {
                uiState.openDialogFavorite.value = false
            }) {
                AlertDialog(
                    title = stringResource(id = R.string.menu_title_dialog),
                    text = uiState.item.collectAsState().value.textPlus,
                    confirm = stringResource(id = R.string.menu_button_dialog),
                    spotify = stringResource(id = R.string.menu_button_dialog_spotify),
                    onClickSucess = {
                        uiState.openDialogFavorite.value = false
                    },
                    onClickSpotify = onClickSpotify
                )
            }
    }
}

@Composable
fun ScreenContent(
    uiState: UiState,
    onClickItem: (Int) -> Unit,
    onEmpty: () -> Unit,
    onSwipeRightItem: (ItemCardVO) -> Unit,
    onSwipeLeftItem: (ItemCardVO) -> Unit,
    onClickChat: () -> Unit,
    onClickBlock: () -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
) {
    SpacerVertical(dp = Size.Size50)
    Header(
        uiState = uiState,
        onClickChat = onClickChat,
        onClickBlock = onClickBlock
    )
    SpacerVertical(dp = Size.Size16)
    ScreenTwyper(
        uiState = uiState,
        onClickItem = onClickItem,
        onSwipeRightItem = onSwipeRightItem,
        onSwipeLeftItem = onSwipeLeftItem,
        onEmpty = onEmpty
    )
    SpacerVertical(dp = Size.Size16)
}

@Composable
fun Header(
    uiState: UiState,
    onClickChat: () -> Unit,
    onClickBlock: () -> Unit,
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
        if (uiState.item.collectAsState().value.imageProfile.isNotEmpty()) {
            ImageCircle(
                uiState.item.collectAsState().value.imageProfile,
                contentDescription = stringResource(id = R.string.accessibily_menu_profile)
            )
        }
        SpacerHorizontal(dp = Size.Size16)
        Text(
            modifier = Modifier
                .padding(start = Size.Size32)
                .weight(Weight.Weight_1),
            text = uiState.item.collectAsState().value.loverName,
            fontSize = Font.Font30,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
        )
        SpacerHorizontal(dp = Size.Size8)
        Row {
            IconButton(
                onClick = onClickChat
            ) {
                Icon(
                    Icons.Rounded.Forum,
                    contentDescription = stringResource(id = R.string.accessibily_menu_chat)
                )
            }
            IconButton(
                onClick = onClickBlock
            ) {
                Icon(
                    Icons.Rounded.Favorite,
                    tint = Color.Red,
                    contentDescription = stringResource(id = R.string.accessibily_menu_plus)
                )
            }
        }
    }
}

@Composable
private fun ScreenTwyper(
    uiState: UiState,
    onClickItem: (Int) -> Unit,
    onSwipeRightItem: (ItemCardVO) -> Unit,
    onSwipeLeftItem: (ItemCardVO) -> Unit,
    onEmpty: () -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = Size.Size8),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Twyper(
        items = uiState.item.collectAsState().value.cardsVO,
        stackCount = STACKCOUNT,
        paddingBetweenCards = PADDING,
        twyperController = uiState.twyperController.collectAsState().value, // optional
        onItemRemoved = { item, direction ->
            when (direction) {
                SwipedOutDirection.LEFT -> onSwipeLeftItem.invoke(item)
                SwipedOutDirection.RIGHT -> onSwipeRightItem.invoke(item)
            }
        },
        onEmpty = onEmpty
    ) { item ->
        Item(
            it = item,
            onClickItem = onClickItem
        )
    }
}

@Composable
fun Item(
    it: ItemCardVO,
    onClickItem: (Int) -> Unit,
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .rippleClickable {
            onClickItem.invoke(it.id)
        },
    backgroundColor = ColorPalette.White
) {
    Column {
        ImageLoader(
            modifier = Modifier
                .fillMaxWidth()
                .height(getHeightImage()),
            image = it.image,
            contentDescription = it.name
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
            maxLines = MAX_LINE_THREE
        )
        SpacerVertical(dp = Size.Size8)
    }
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
fun AlertDialog(
    title: String,
    text: String,
    confirm: String,
    spotify: String,
    onClickSucess: () -> Unit,
    onClickSpotify: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onClickSucess)
            { Text(text = confirm) }
        },
        dismissButton = {
            TextButton(onClick = onClickSpotify)
            { Text(text = spotify) }
        },
        title = { Text(text = title) },
        text = { Text(text = text) }
    )
}

@Composable
fun getHeightImage(): Dp =
    if (LocalConfiguration.current.screenHeightDp < HEIGHT_BASE) {
        (LocalConfiguration.current.screenHeightDp / HEIGHT_MIN).dp
    } else {
        (LocalConfiguration.current.screenHeightDp / HEIGHT_MAX).dp
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
