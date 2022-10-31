package com.wolfdeveloper.wolfdevlovers.application.feature.dashboard

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainActivity
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenEvent
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.UiState
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenState.ScreenDashBoard
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenState.ScreenLoading
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenState.ScreenContent
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenState.ScreenSuccess
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenState.ScreenError
import com.wolfdeveloper.wolfdevlovers.commons.extensions.MAX_LINE_DEFAULT
import com.wolfdeveloper.wolfdevlovers.commons.extensions.isNull
import com.wolfdeveloper.wolfdevlovers.dsc.color.ColorPalette
import com.wolfdeveloper.wolfdevlovers.dsc.component.SpacerHorizontal
import com.wolfdeveloper.wolfdevlovers.dsc.component.SpacerVertical
import com.wolfdeveloper.wolfdevlovers.dsc.component.rippleClickable
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Font
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Radius
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size
import androidx.compose.ui.res.stringResource

@Composable
fun DashboardPageScreen(
    viewModel: DashboardViewModel,
    flowViewModel: MainViewModel
) {
    val activity = LocalContext.current as MainActivity
    val clipboardManager = LocalClipboardManager.current

    LaunchedEffect(viewModel) {
        viewModel.setup()
    }

    viewModel.uiState.screenState.let {
        BackHandler(
            enabled = it.collectAsState().value != ScreenDashBoard,
            onBack = viewModel::onClickBackSpace
        )
    }

    Screen(
        uiState = viewModel.uiState,
        onClickCode = {
            clipboardManager.setText(AnnotatedString(viewModel.uiState.code.value))
            Toast.makeText(
                activity,
                R.string.dash_success_code_copy,
                Toast.LENGTH_LONG
            )
        },
        onClick = {
            viewModel.insert(activity = activity)
        },
        onClickAccess = viewModel::onClickAccess,
        onClickInsert = viewModel::onClickInsert
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
    onClick: () -> Unit,
    onClickCode: () -> Unit,
    onClickAccess: () -> Unit,
    onClickInsert: () -> Unit,
) = MaterialTheme {
    Box(modifier = Modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.Transparent,
        ) {
            val screenState by uiState.screenState.collectAsState()
            when (screenState) {
                is ScreenDashBoard -> ScreenDashBoard(
                    uiState = uiState,
                    onClickAccess = onClickAccess,
                    onClickInsert = onClickInsert,
                )
                is ScreenLoading -> ScreenProgress()
                is ScreenContent -> ScreenContent(
                    uiState = uiState,
                    onClick = onClick
                )
                is ScreenSuccess -> ScreenSuccess(
                    uiState = uiState,
                    onClickAccess = onClickAccess,
                    onClickCode = onClickCode
                )
                is ScreenError -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenDashBoard(
    uiState: UiState,
    onClickAccess: () -> Unit,
    onClickInsert: () -> Unit,
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    BottomSheetScaffold(
        scaffoldState = BottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(
                initialValue = BottomSheetValue.Expanded,
                confirmStateChange = { return@BottomSheetState false }
            ),
            snackbarHostState = SnackbarHostState(),
            drawerState = DrawerState(
                initialValue = DrawerValue.Open,
                confirmStateChange = { return@DrawerState false }
            )
        ),
        sheetShape = RoundedCornerShape(
            topStart = Size.Size40,
            topEnd = Size.Size40
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Size.Size450),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpacerVertical(dp = Size.Size32)
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Size.Size32),
                    value = uiState.code.value,
                    onValueChange = {
                        if (it.length <= MAX_CARACHTER) {
                            uiState.code.value = it
                        }
                    },
                    label = { Text(stringResource(id = R.string.dash_code)) },
                    maxLines = MAX_LINE_DEFAULT,
                    textStyle = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Normal
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent
                    )
                )
                SpacerVertical(dp = Size.Size32)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Size.Size32),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClickAccess,
                        shape = RoundedCornerShape(PERCENT_RADIUS_BUTTON),
                        contentPadding = PaddingValues(
                            start = Size.Size32,
                            end = Size.Size32,
                            top = Size.Size16,
                            bottom = Size.Size16
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(Size.Size16),
                            painter = painterResource(id = R.drawable.ic_padlock_open),
                            contentDescription = stringResource(id = R.string.dash_button_access),
                            tint = Color.Unspecified
                        )
                        Spacer(Modifier.size(Size.Size8))
                        Text(stringResource(id = R.string.dash_button_access))
                    }
                }
                SpacerVertical(dp = Size.Size32)
                ClickableText(
                    AnnotatedString(stringResource(id = R.string.dash_button_create)),
                    style = TextStyle(
                        color = ColorPalette.SteelGrey,
                        fontSize = Font.Font16,
                        fontFamily = FontFamily.SansSerif
                    ),
                    onClick = { onClickInsert.invoke() }
                )

                if (uiState.validate.collectAsState().value.isNotEmpty()) {
                    SpacerVertical(dp = Size.Size64)
                    Text(
                        text = stringResource(id = R.string.dash_validate_code),
                        fontSize = Font.Font16,
                        color = ColorPalette.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.SansSerif
                    )
                    SpacerVertical(dp = Size.Size8)
                    Text(
                        text = uiState.validate.collectAsState().value,
                        fontSize = Font.Font16,
                        color = ColorPalette.Black,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.SansSerif
                    )
                    SpacerVertical(dp = Size.Size64)
                }
            }
        }
    ) {
        Image(
            painter = painterResource(R.drawable.image_wolf),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ScreenContent(
    uiState: UiState,
    onClick: () -> Unit
) = Box(modifier = Modifier.fillMaxSize()) {
    Image(
        painter = painterResource(R.drawable.image_wolf),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier.fillMaxSize()
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Size.Size8)
            .verticalScroll(rememberScrollState())
    ) {
        SpacerVertical(WindowInsets.systemBars.asPaddingValues().calculateTopPadding())
        SpacerVertical(Size.Size16)
        Card(
            shape = RoundedCornerShape(Radius.Radius8)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                SpacerVertical(dp = Size.Size16)
                Header()
                SpacerVertical(dp = Size.Size16)
                formData(uiState)
                SpacerVertical(dp = Size.Size16)
                TitlePageView()
                SpacerVertical(dp = Size.Size16)
                HorizontalPager(
                    count = uiState.lovers.size,
                    state = rememberPagerState(),
                ) {
                    formDataLovers(lover = uiState.lovers[this.currentPage], this.currentPage + ONE)
                }
                SpacerVertical(dp = Size.Size16)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Size.Size32),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClick,
                        shape = RoundedCornerShape(PERCENT_RADIUS_BUTTON),
                        contentPadding = PaddingValues(
                            start = Size.Size32,
                            end = Size.Size32,
                            top = Size.Size16,
                            bottom = Size.Size16
                        )
                    ) {

                        Text(stringResource(id = R.string.dash_button))
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = stringResource(id = R.string.dash_button_next),
                            tint = Color.Unspecified
                        )
                    }
                }
                SpacerVertical(dp = Size.Size16)
            }
        }
        SpacerVertical(Size.Size16)
    }
}

@Composable
fun Header() = Column(
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
    Text(
        text = stringResource(id = R.string.app_name),
        fontSize = Font.Font30,
        color = ColorPalette.System,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.Cursive,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun formData(uiState: UiState) = Column(
    modifier = Modifier
        .padding(
            top = Size.Size4,
            bottom = Size.Size4,
            start = Size.Size16,
            end = Size.Size16,
        )
        .fillMaxWidth()
) {
    //Textfield Name
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.name.value,
        onValueChange = {
            if (it.length <= MAX_CARACHTER) {
                uiState.name.value = it
            }
        },
        label = { Text(stringResource(id = R.string.dash_name)) },
        maxLines = MAX_LINE_DEFAULT,
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
    SpacerVertical()
    //Textfield Plus
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.plus.value,
        onValueChange = {
            if (it.length <= MAX_CARACHTER_PLUS) {
                uiState.plus.value = it
            }
        },
        label = { Text(stringResource(id = R.string.dash_plus)) },
        maxLines = MAX_LINE_PLUS,
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
    SpacerVertical()
    //Textfield Spotify
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.linkPlus.value,
        onValueChange = {
            if (it.length <= MAX_CARACHTER_PLUS) {
                uiState.linkPlus.value = it
            }
        },
        label = { Text(stringResource(id = R.string.dash_link_plus)) },
        maxLines = MAX_LINE_DEFAULT,
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
    SpacerVertical()
    //Textfield Instagram
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.socialMediaLink.value,
        onValueChange = {
            if (it.length <= MAX_CARACHTER) {
                uiState.socialMediaLink.value = it
            }
        },
        label = { Text(stringResource(id = R.string.dash_instagram)) },
        maxLines = MAX_LINE_PLUS,
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
    SpacerVertical()
    //Textfield Whatssap
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.whatssap.value,
        onValueChange = {
            if (it.length <= MAX_CARACHTER) {
                uiState.whatssap.value = it
            }
        },
        label = { Text(stringResource(id = R.string.dash_whatssap)) },
        maxLines = MAX_LINE_PLUS,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        )
    )
    SpacerVertical()
    Row(
        Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        ImageProfile(
            uiState
        )
        SpacerHorizontal(Size.Size32)
        ImageBackground(
            uiState
        )
    }
    SpacerVertical()
    DropDownTimeLife(uiState = uiState)
}

@Composable
fun ImageProfile(uiState: UiState) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uiState.profileImage.value = uri
    }
    return Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(Radius.Radius8),
        backgroundColor = ColorPalette.White
    ) {
        Column(
            modifier = Modifier
                .rippleClickable {
                    launcher.launch(LAUNCH_IMAGE)
                }
                .padding(Size.Size8),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.dash_image_profile),
                fontSize = Font.Font16,
                color = ColorPalette.System,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
            SpacerVertical(Size.Size8)
            if (uiState.profileImage.collectAsState().value.isNull()) {
                Icon(
                    modifier = Modifier.size(Size.Size100),
                    painter = painterResource(id = R.drawable.ic_add_image),
                    contentDescription = stringResource(id = R.string.accessibily_dash_add_image),
                    tint = Color.Unspecified
                )
            } else {
                Image(
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uiState.profileImage.value)
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(Size.Size100)
                )
            }
        }
    }
}

@Composable
fun ImageBackground(uiState: UiState) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uiState.backgroundImage.value = uri
    }
    return Card(
        modifier = Modifier
            .fillMaxSize(),
        shape = RoundedCornerShape(Radius.Radius8),
        backgroundColor = ColorPalette.White
    ) {
        Column(
            modifier = Modifier
                .rippleClickable {
                    launcher.launch(LAUNCH_IMAGE)
                }
                .padding(Size.Size8),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.dash_image_background),
                fontSize = Font.Font16,
                color = ColorPalette.System,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold
            )
            SpacerVertical(Size.Size8)
            if (uiState.backgroundImage.collectAsState().value.isNull()) {
                Icon(
                    modifier = Modifier.size(Size.Size100),
                    painter = painterResource(id = R.drawable.ic_add_image),
                    contentDescription = stringResource(id = R.string.accessibily_dash_add_image),
                    tint = Color.Unspecified
                )
            } else {
                Image(
                    rememberAsyncImagePainter(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uiState.backgroundImage.value)
                            .build()
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(Size.Size100)
                )
            }
        }
    }
}

@Composable
fun TitlePageView() = Column(
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
    Text(
        text = stringResource(id = R.string.menu_toast),
        fontSize = Font.Font20,
        color = ColorPalette.System,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun DropDownTimeLife(uiState: UiState) = uiState.run {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .padding(
                top = Size.Size4,
                bottom = Size.Size4
            )
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                readOnly = true,
                value = selectedTimeLife.value.first,
                onValueChange = { },
                label = { Text(stringResource(id = R.string.dash_time_life)) },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {
                uiState.optionsTimeLife.forEach { selectionOption ->
                    DropdownMenuItem(
                        onClick = {
                            selectedTimeLife.value = selectionOption
                            expanded = false
                        }
                    ) {
                        Text(text = selectionOption.first)
                    }
                }
            }
        }
    }
}

@Composable
private fun formDataLovers(lover: UiState.LoverState, index: Int) = Column(
    modifier = Modifier.padding(Size.Size16),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        shape = RoundedCornerShape(Radius.Radius8),
        backgroundColor = ColorPalette.White
    ) {
        Column(
            modifier = Modifier.padding(Size.Size16),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = index.toString(),
                fontSize = Font.Font20,
                color = ColorPalette.System,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal
            )
            SpacerVertical(Size.Size8)
            //Textfield Text
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(
                        Size.Size350
                    ),
                value = lover.text.value,
                onValueChange = {
                    if (it.length <= MAX_CARACHTER_TEXT) {
                        lover.text.value = it
                    }
                },
                label = { Text(stringResource(id = R.string.dash_his_text)) },
                maxLines = MAX_LINE_TEXT,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            SpacerVertical(Size.Size8)
            //Textfield Link
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(
                        Size.Size350
                    ),
                value = lover.link.value,
                onValueChange = {
                    if (it.length <= MAX_CARACHTER) {
                        lover.link.value = it
                    }
                },
                label = { Text(stringResource(id = R.string.dash_his_music)) },
                maxLines = MAX_LINE_DEFAULT,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Normal
                ),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent
                )
            )
            SpacerVertical()
            ImageLover(lover)
        }
    }
}

@Composable
fun ImageLover(lover: UiState.LoverState) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        lover.image.value = uri
    }
    return Column(
        modifier = Modifier
            .fillMaxWidth()
            .rippleClickable {
                launcher.launch(LAUNCH_IMAGE)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.dash_image_lover),
            fontSize = Font.Font16,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal
        )
        SpacerVertical(Size.Size8)
        if (lover.image.collectAsState().value.isNull()) {
            Icon(
                modifier = Modifier.size(Size.Size256),
                painter = painterResource(id = R.drawable.ic_add_image),
                contentDescription = stringResource(id = R.string.accessibily_dash_add_image),
                tint = Color.Unspecified
            )
        } else {
            Image(
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(lover.image.value)
                        .build()
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(Size.Size256)
            )
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
        Text(stringResource(id = R.string.dash_loading))
        SpacerVertical(dp = Size.Size32)
        CircularProgressIndicator(
            modifier = Modifier.size(Size.SizeProgress),
            color = ColorPalette.Black
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun ScreenSuccess(
    uiState: UiState,
    onClickAccess: () -> Unit,
    onClickCode: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    BottomSheetScaffold(
        scaffoldState = BottomSheetScaffoldState(
            bottomSheetState = BottomSheetState(
                initialValue = BottomSheetValue.Expanded,
                confirmStateChange = { return@BottomSheetState false }
            ),
            snackbarHostState = SnackbarHostState(),
            drawerState = DrawerState(
                initialValue = DrawerValue.Open,
                confirmStateChange = { return@DrawerState false }
            )
        ),
        sheetShape = RoundedCornerShape(
            topStart = Size.Size40,
            topEnd = Size.Size40
        ),
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(Size.Size450),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpacerVertical(dp = Size.Size32)
                Text(
                    modifier = Modifier.rippleClickable {
                        onClickCode.invoke()
                    },
                    text = stringResource(id = R.string.dash_success_code, uiState.code.value),
                    fontSize = Font.Font25,
                    color = ColorPalette.Black,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Normal
                )
                SpacerVertical(dp = Size.Size32)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Size.Size32),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClickCode,
                        shape = RoundedCornerShape(PERCENT_RADIUS_BUTTON),
                        contentPadding = PaddingValues(
                            start = Size.Size32,
                            end = Size.Size32,
                            top = Size.Size16,
                            bottom = Size.Size16
                        )
                    ) {
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(id = R.drawable.ic_copy),
                            contentDescription = stringResource(id = R.string.dash_button_copy),
                            tint = Color.Unspecified
                        )
                        Spacer(Modifier.size(Size.Size8))
                        Text(stringResource(id = R.string.dash_button_copy))
                    }
                }
                SpacerVertical(dp = Size.Size32)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Size.Size32),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = onClickAccess,
                        shape = RoundedCornerShape(PERCENT_RADIUS_BUTTON),
                        contentPadding = PaddingValues(
                            start = Size.Size32,
                            end = Size.Size32,
                            top = Size.Size16,
                            bottom = Size.Size16
                        )
                    ) {

                        Text(stringResource(id = R.string.dash_button_next))
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Icon(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            painter = painterResource(id = R.drawable.ic_next),
                            contentDescription = stringResource(id = R.string.dash_button_next),
                            tint = Color.Unspecified
                        )
                    }
                }
            }
        }) {
        Image(
            painter = painterResource(R.drawable.image_wolf),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun EventConsumer(
    activity: MainActivity,
    viewModel: DashboardViewModel,
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

private const val ONE = 1
private const val MAX_LINE_PLUS = 5
private const val MAX_CARACHTER = 100
private const val MAX_CARACHTER_PLUS = 250
private const val MAX_LINE_TEXT = 50
private const val PERCENT_RADIUS_BUTTON = 50
private const val MAX_CARACHTER_TEXT = 1000
private const val LAUNCH_IMAGE = "image/*"
