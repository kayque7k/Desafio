package com.wolfdeveloper.wolfdevlovers.application.feature.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.application.feature.detail.DetailViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainActivity
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.menu.MenuViewModel.ScreenEvent
import com.wolfdeveloper.wolfdevlovers.application.feature.menu.MenuViewModel.UiState
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemCardVO
import com.wolfdeveloper.wolfdevlovers.commons.extensions.MAX_LINE_DEFAULT
import com.wolfdeveloper.wolfdevlovers.commons.extensions.MAX_LINE_THREE
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO
import com.wolfdeveloper.wolfdevlovers.dsc.color.ColorPalette
import com.wolfdeveloper.wolfdevlovers.dsc.component.SpacerVertical
import com.wolfdeveloper.wolfdevlovers.dsc.component.ImageCircle
import com.wolfdeveloper.wolfdevlovers.dsc.component.SpacerHorizontal
import com.wolfdeveloper.wolfdevlovers.dsc.component.rippleClickable
import com.wolfdeveloper.wolfdevlovers.dsc.component.ImageLoader
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Font
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Radius
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Weight

private const val HEIGHT_BASE = 600
private const val HEIGHT_MIN = 1.5
private const val HEIGHT_MAX = 2

@Composable
fun MenuPageScreen(
    viewModel: MenuViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity

    LaunchedEffect(viewModel) {
        viewModel.apply {
            getUser(
                activity = activity
            )
        }
    }

    Screen(
        uiState = viewModel.uiState,
        onClickItem = {
            flowViewModel.setIdLover(it)
            viewModel.onClickItem()
        },
        onClickChat = { viewModel.onClickChat(activity = activity) },
        onClickLink = { viewModel.onClickLink(activity = activity, url = it) },
        onClickInstagram = { viewModel.onClickInstagram(activity = activity) },
        onClickAccess = { viewModel.onClickAccess(activity = activity) },
        onClickBlock = viewModel::onClickBlock,
        onClickinsert = viewModel::onClickinsert,
        onRetry = { viewModel.onRetry(activity) }
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
    onClickChat: () -> Unit,
    onClickInstagram: () -> Unit,
    onClickAccess: () -> Unit,
    onClickLink: (url: String) -> Unit,
    onClickBlock: () -> Unit,
    onClickinsert: () -> Unit,
    onRetry: () -> Unit
) = MaterialTheme {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (uiState.item.collectAsState().value.imageBackground.isNotEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(uiState.item.collectAsState().value.imageBackground)
                        .build()
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
                    onClickChat = onClickChat,
                    onClickInstagram = onClickInstagram,
                    onClickBlock = onClickBlock,
                    onClickinsert = onClickinsert,
                    onClickLink = onClickLink
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
                    isSpotify = uiState.item.collectAsState().value.linkPlus.isNotEmpty(),
                    confirm = stringResource(id = R.string.menu_button_dialog),
                    access = stringResource(id = R.string.menu_button_dialog_access),
                    onClickSucess = {
                        uiState.openDialogFavorite.value = false
                    },
                    onClickAccess = onClickAccess
                )
            }
    }
}

@Composable
fun ScreenContent(
    uiState: UiState,
    onClickItem: (Int) -> Unit,
    onClickInstagram: () -> Unit,
    onClickChat: () -> Unit,
    onClickBlock: () -> Unit,
    onClickinsert: () -> Unit,
    onClickLink: (url: String) -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
) {
    SpacerVertical(dp = WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
    SpacerVertical(dp = Size.Size8)
    Header(
        uiState = uiState,
        onClickInstagram = onClickInstagram,
        onClickChat = onClickChat,
        onClickBlock = onClickBlock,
        onClickinsert = onClickinsert
    )
    ScreenCards(
        uiState = uiState,
        onClickItem = onClickItem,
        onClickLink = onClickLink
    )
    SpacerVertical(dp = Size.Size16)
}

@Composable
fun Header(
    uiState: UiState,
    onClickInstagram: () -> Unit,
    onClickChat: () -> Unit,
    onClickBlock: () -> Unit,
    onClickinsert: () -> Unit,
) = Column(
    modifier = Modifier
        .padding(
            horizontal = Size.Size8
        )
        .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Row {
        if (uiState.item.collectAsState().value.imageProfile.isNotEmpty()) {
            ImageCircle(
                modifier = Modifier.rippleClickable {
                    onClickInstagram.invoke()
                },
                url = uiState.item.collectAsState().value.imageProfile,
                contentDescription = stringResource(id = R.string.accessibily_menu_profile)
            )
        }
        SpacerHorizontal(dp = Size.Size8)
        Text(
            modifier = Modifier
                .padding(start = Size.Size16)
                .weight(Weight.Weight_1),
            text = uiState.item.collectAsState().value.name,
            maxLines = MAX_LINE_DEFAULT,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            fontSize = Font.Font30,
            fontFamily = FontFamily.SansSerif,
            overflow = TextOverflow.Ellipsis
        )
        SpacerHorizontal(dp = Size.Size8)
        Row {
            IconButton(
                onClick = onClickinsert
            ) {
                Icon(
                    modifier = Modifier.size(Size.Size24),
                    painter = painterResource(id = R.drawable.ic_padlock_open),
                    contentDescription = stringResource(id = R.string.accessibily_menu_insert),
                    tint = Color.Unspecified
                )
            }
            if (uiState.item.collectAsState().value.number.isNotEmpty()) {
                IconButton(
                    onClick = onClickChat
                ) {
                    Icon(
                        modifier = Modifier.size(Size.Size32),
                        painter = painterResource(id = R.drawable.ic_whatsapp),
                        contentDescription = stringResource(id = R.string.accessibily_menu_chat),
                        tint = Color.Unspecified
                    )
                }
            }
            if (uiState.item.collectAsState().value.textPlus.isNotEmpty()) {
                IconButton(
                    onClick = onClickBlock
                ) {
                    Icon(
                        modifier = Modifier.size(Size.Size24),
                        painter = painterResource(id = R.drawable.ic_star),
                        contentDescription = stringResource(id = R.string.accessibily_menu_plus),
                        tint = Color.Unspecified
                    )
                }
            }
        }
    }
}

@Composable
private fun ScreenCards(
    uiState: UiState,
    onClickItem: (Int) -> Unit,
    onClickLink: (url: String) -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .padding(end = Size.Size8, start = Size.Size8, top = Size.Size8)
        .verticalScroll(rememberScrollState())
) {
    uiState.item.collectAsState().value.cardsVO.reversed().forEachIndexed { index, it ->
        Item(
            it = it,
            onClickItem = onClickItem,
            first = index == ZERO,
            onClickLink = onClickLink
        )
    }
}

@Composable
fun Item(
    it: ItemCardVO,
    onClickItem: (Int) -> Unit,
    onClickLink: (url: String) -> Unit,
    first: Boolean = false
) = Card(
    modifier = Modifier
        .fillMaxWidth()
        .padding(
            bottom = Size.Size16, top = if (first) {
                Size.Size16
            } else {
                Size.Size2
            }
        )
        .rippleClickable {
            onClickItem.invoke(it.id)
        },
    shape = RoundedCornerShape(Radius.Radius8),
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
    HeaderCard(itenCardVO = it, onClickLink = onClickLink)
}


@Composable
fun HeaderCard(
    itenCardVO: ItemCardVO,
    onClickLink: (url: String) -> Unit,
) = Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.Top,
    horizontalArrangement = Arrangement.End,
) {
    if (itenCardVO.link.isNotEmpty()) {
        IconButton(
            modifier = Modifier
                .padding(Size.Size8),
            onClick = {
                onClickLink.invoke(
                    itenCardVO.link
                )
            }
        ) {
            Icon(
                modifier = Modifier.size(Size.Size24),
                painter = painterResource(id = R.drawable.ic_link),
                contentDescription = stringResource(id = R.string.accessibily_details_play),
                tint = Color.Unspecified
            )
        }
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
            color = ColorPalette.Black
        )
    }
}

@Composable
fun AlertDialog(
    title: String,
    text: String,
    confirm: String,
    access: String,
    isSpotify: Boolean,
    onClickSucess: () -> Unit,
    onClickAccess: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onClickSucess)
            { Text(text = confirm) }
        },
        dismissButton = {
            if (isSpotify) {
                TextButton(onClick = onClickAccess)
                { Text(text = access) }
            }
        },
        title = {
            Row {
                Icon(
                    modifier = Modifier.size(Size.Size16),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = stringResource(id = R.string.accessibily_menu_plus),
                    tint = Color.Unspecified
                )
                SpacerHorizontal(dp = Size.Size8)
                Text(text = title)
                SpacerHorizontal(dp = Size.Size8)
                Icon(
                    modifier = Modifier.size(Size.Size16),
                    painter = painterResource(id = R.drawable.ic_star),
                    contentDescription = stringResource(id = R.string.accessibily_menu_plus),
                    tint = Color.Unspecified
                )
            }
        },
        text = {
            Text(
                text = text,
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Ellipsis
            )
        }
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
