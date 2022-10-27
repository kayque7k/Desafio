package com.wolfdeveloper.wolfdevlovers.application.feature.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.NextPlan
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.rounded.Add
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
import com.github.theapache64.twyper.SwipedOutDirection
import com.github.theapache64.twyper.Twyper
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainActivity
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.menu.MenuViewModel.ScreenEvent
import com.wolfdeveloper.wolfdevlovers.application.feature.menu.MenuViewModel.UiState
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemCardVO
import com.wolfdeveloper.wolfdevlovers.commons.extensions.MAX_LINE_DEFAULT
import com.wolfdeveloper.wolfdevlovers.commons.extensions.MAX_LINE_THREE
import com.wolfdeveloper.wolfdevlovers.dsc.color.ColorPalette
import com.wolfdeveloper.wolfdevlovers.dsc.component.SpacerVertical
import com.wolfdeveloper.wolfdevlovers.dsc.component.ImageCircle
import com.wolfdeveloper.wolfdevlovers.dsc.component.SpacerHorizontal
import com.wolfdeveloper.wolfdevlovers.dsc.component.rippleClickable
import com.wolfdeveloper.wolfdevlovers.dsc.component.ImageLoader
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Font
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Weight

private const val STACKCOUNT = 10
private const val PADDING = 7f
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
        viewModel.getUser(
            activity = activity
        )
    }

    Screen(
        uiState = viewModel.uiState,
        onClickItem = {
            flowViewModel.setIdLover(it)
            viewModel.onClickItem()
        },
        onEmpty = {
            viewModel.onEmpty(activity = activity)
        },
        onSwipeRightItem = viewModel::onSwipeRightItem,
        onSwipeLeftItem = viewModel::onSwipeLeftItem,
        onSwipeNextItem = viewModel::onSwipeNextItem,
        onClickChat = { viewModel.onClickChat(activity = activity) },
        onClickInstagram = { viewModel.onClickInstagram(activity = activity) },
        onClickSpotify = { viewModel.onClickSpotify(activity = activity) },
        onClickBlock = viewModel::onClickBlock,
        onClickinsert = viewModel::onClickinsert,
        onRetry = {
            viewModel.onRetry(activity)
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
    onSwipeLeftItem: () -> Unit,
    onSwipeNextItem: () -> Unit,
    onClickChat: () -> Unit,
    onClickInstagram: () -> Unit,
    onClickSpotify: () -> Unit,
    onClickBlock: () -> Unit,
    onClickinsert: () -> Unit,
    onRetry: () -> Unit
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
                    onSwipeNextItem = onSwipeNextItem,
                    onClickChat = onClickChat,
                    onClickInstagram = onClickInstagram,
                    onClickBlock = onClickBlock,
                    onClickinsert = onClickinsert
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
    onSwipeLeftItem: () -> Unit,
    onSwipeNextItem: () -> Unit,
    onClickInstagram: () -> Unit,
    onClickChat: () -> Unit,
    onClickBlock: () -> Unit,
    onClickinsert: () -> Unit
) = Scaffold(
    modifier = Modifier
        .fillMaxSize(),
    floatingActionButton = {
        FloatingActionButton(
            onClick = { onSwipeNextItem.invoke() },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = MaterialTheme.colors.onPrimary
        ) {
            Icon(Icons.Filled.Redo, stringResource(id = R.string.accessibily_menu_next))
        }
    },
    floatingActionButtonPosition = FabPosition.End,
    isFloatingActionButtonDocked = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        SpacerVertical(dp = Size.Size50)
        Header(
            uiState = uiState,
            onClickInstagram = onClickInstagram,
            onClickChat = onClickChat,
            onClickBlock = onClickBlock,
            onClickinsert = onClickinsert
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
                modifier = Modifier.rippleClickable {
                    onClickInstagram.invoke()
                },
                url = uiState.item.collectAsState().value.imageProfile,
                contentDescription = stringResource(id = R.string.accessibily_menu_profile)
            )
        }
        SpacerHorizontal(dp = Size.Size16)
        Text(
            modifier = Modifier
                .padding(start = Size.Size32)
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
private fun ScreenTwyper(
    uiState: UiState,
    onClickItem: (Int) -> Unit,
    onSwipeRightItem: (ItemCardVO) -> Unit,
    onSwipeLeftItem: () -> Unit,
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
        twyperController = uiState.twyperController.collectAsState().value,
        onItemRemoved = { item, direction ->
            when (direction) {
                SwipedOutDirection.LEFT -> onSwipeLeftItem.invoke()
                SwipedOutDirection.RIGHT -> onSwipeRightItem.invoke(item)
            }
        },
        onEmpty = onEmpty,

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
    onClickSpotify: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { },
        confirmButton = {
            TextButton(onClick = onClickSucess)
            { Text(text = confirm) }
        },
        dismissButton = {
            if (isSpotify) {
                TextButton(onClick = onClickSpotify)
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
