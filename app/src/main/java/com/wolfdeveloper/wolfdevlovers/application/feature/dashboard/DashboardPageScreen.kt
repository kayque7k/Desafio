package com.wolfdeveloper.wolfdevlovers.application.feature.dashboard

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.Text
import androidx.compose.material.Icon
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import coil.compose.rememberImagePainter
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
import com.wolfdeveloper.wolfdevlovers.dsc.dimen.Size

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

@Composable
private fun ScreenDashBoard(
    uiState: UiState,
    onClickAccess: () -> Unit,
    onClickInsert: () -> Unit,
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        )
        SpacerVertical(dp = Size.Size32)
        Button(
            onClick = onClickAccess,
            contentPadding = PaddingValues(
                start = Size.Size32,
                end = Size.Size32,
                top = Size.Size16,
                bottom = Size.Size16
            )
        ) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = R.drawable.ic_padlock_open),
                contentDescription = stringResource(id = R.string.dash_button_access),
                tint = Color.Unspecified
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(id = R.string.dash_button_access))
        }
        SpacerVertical(dp = Size.Size32)
        Button(
            onClick = onClickInsert,
            contentPadding = PaddingValues(
                start = Size.Size32,
                end = Size.Size32,
                top = Size.Size16,
                bottom = Size.Size16
            )
        ) {
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = stringResource(id = R.string.dash_button_create),
                tint = Color.Unspecified
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(id = R.string.dash_button_create))
        }
    }
}

@Composable
fun ScreenContent(
    uiState: UiState,
    onClick: () -> Unit
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
) {
    SpacerVertical(dp = Size.Size32)
    Header()
    SpacerVertical(dp = Size.Size16)
    formData(uiState)
    SpacerVertical(dp = Size.Size16)
    TitleLover()
    SpacerVertical(dp = Size.Size16)
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        uiState.lovers.forEachIndexed { index, it ->
            formDataLovers(lover = it, index + ONE)
        }
    }
    SpacerVertical(dp = Size.Size16)
    Button(onClick)
    SpacerVertical(dp = Size.Size32)
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
        fontSize = Font.Font25,
        color = ColorPalette.Black,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
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
    )
    SpacerVertical()
    //Textfield Name Lover
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = uiState.nameLover.value,
        onValueChange = {
            if (it.length <= MAX_CARACHTER) {
                uiState.nameLover.value = it
            }
        },
        label = { Text(stringResource(id = R.string.dash_his_name)) },
        maxLines = MAX_LINE_DEFAULT,
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
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
        label = { Text(stringResource(id = R.string.dash_spotify)) },
        maxLines = MAX_LINE_DEFAULT,
        textStyle = TextStyle(
            color = Color.Black,
            fontWeight = FontWeight.Normal
        ),
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
}

@Composable
fun ImageProfile(uiState: UiState) {
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uiState.profileImage.value = uri
    }
    return Column(
        modifier = Modifier.rippleClickable {
            launcher.launch(LAUNCH_IMAGE)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.dash_image_profile),
            fontSize = Font.Font16,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal
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
                painter = rememberImagePainter(
                    uiState.profileImage.value
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(Size.Size100)
            )
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
    return Column(
        modifier = Modifier.rippleClickable {
            launcher.launch(LAUNCH_IMAGE)
        },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.dash_image_background),
            fontSize = Font.Font16,
            color = ColorPalette.Black,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal
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
                painter = rememberImagePainter(
                    uiState.backgroundImage.value
                ),
                contentDescription = null,
                modifier = Modifier.size(Size.Size100)
            )
        }
    }
}

@Composable
fun TitleLover() = Column(
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
        fontSize = Font.Font16,
        color = ColorPalette.Black,
        textAlign = TextAlign.Center,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun formDataLovers(lover: UiState.LoverState, index: Int) = Column(
    modifier = Modifier
        .padding(
            top = Size.Size4,
            bottom = Size.Size4,
            start = Size.Size16,
            end = Size.Size16,
        )
        .fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Text(
        text = index.toString(),
        fontSize = Font.Font16,
        color = ColorPalette.Black,
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
    )
    SpacerVertical()
    //Textfield Music
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
    )
    SpacerVertical()
    ImageLover(lover)
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
                painter = rememberImagePainter(
                    lover.image.value
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(Size.Size256)
            )
        }
    }
}

@Composable
private fun Button(onClick: () -> Unit) = Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Button(
        onClick = onClick,
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

@Composable
private fun ScreenSuccess(
    uiState: UiState,
    onClickAccess: () -> Unit,
    onClickCode: () -> Unit
) = Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Button(
            onClick = onClickCode,
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
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(stringResource(id = R.string.dash_button_copy))
        }
        SpacerVertical(dp = Size.Size32)
        Button(
            onClick = onClickAccess,
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
private const val MAX_CARACHTER_TEXT = 700
private const val LAUNCH_IMAGE = "image/*"
