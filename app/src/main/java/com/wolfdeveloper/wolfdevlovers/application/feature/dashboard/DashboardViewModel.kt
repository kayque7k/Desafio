package com.wolfdeveloper.wolfdevlovers.application.feature.dashboard

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Post
import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel.Navigation
import com.wolfdeveloper.wolfdevlovers.application.feature.dashboard.DashboardViewModel.ScreenEvent
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardBackgroundUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardImageLoverUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardInsertUseCase
import com.wolfdeveloper.wolfdevlovers.application.usecase.DashboardProfileUseCase
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.ChannelEventSenderImpl
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.EventSender
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessioInput
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessionOutput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.commons.extensions.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DashboardViewModel(
    private val dashboardInsertUseCase: DashboardInsertUseCase,
    private val dashboardBackgroundUseCase: DashboardBackgroundUseCase,
    private val dashboardImageLoverUseCase: DashboardImageLoverUseCase,
    private val dashboardProfileUseCase: DashboardProfileUseCase,
    private val input: ISessioInput,
    private val output: ISessionOutput,
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = UiState()

    fun setup() = uiState.run {
        output.let {
            val code = it.getCode()
            if (code.isNotEmpty()) {
                this.code.value = code
                if (it.isValidade()) {
                    validate.value = it.getDateFinal().formater()
                } else {
                    validate.value = EMPTY_STRING
                }
            }
        }
    }


    fun insert(activity: Activity) = uiState.run {

        if (name.value.isEmpty()) {
            invalideToast(activity = activity, id = R.string.invalide_name)
            return@run
        }
        if (this@run.profileImage.value.isNull()) {
            invalideToast(activity = activity, id = R.string.invalide_profile)
            return@run
        }
        if (this@run.backgroundImage.value.isNull()) {
            invalideToast(activity = activity, id = R.string.invalide_background)
            return@run
        }
        if (this@run.lovers.filter {
                it.image.value.isNotNull() && it.text.value.isNotEmpty()
            }.isEmpty()) {
            invalideToast(activity = activity, id = R.string.invalide_lovers)
            return@run
        }

        viewModelScope.launch {
            screenState.value = ScreenState.ScreenLoading
            when (val result = dashboardInsertUseCase.execute(
                User().apply {
                    myName = this@run.name.value
                    nameLover = this@run.nameLover.value
                    plus = this@run.plus.value
                    linkPlus = this@run.linkPlus.value
                    whatssap = this@run.whatssap.value
                    socialMediaLink = this@run.socialMediaLink.value
                    timeLife = this@run.selectedTimeLife.value.second
                    posts = this@run.lovers.filter {
                        !it.text.value.isBlank() && it.image.value.isNotNull()
                    }.map {
                        Post().apply {
                            textPublication = it.text.value
                            link = it.link.value
                        }
                    }
                }
            )) {
                is Result.Success -> {
                    if (result.data.id.isZero()) {
                        screenState.value = ScreenState.ScreenContent
                        invalideToast(activity = activity, result.data.code)
                    }

                    this@run.code.value = result.data.code
                    this@run.profileImage.value?.let {
                        dashboardProfileUseCase.execute(
                            uri = it,
                            code = this@run.code.value
                        )
                    }

                    this@run.backgroundImage.value?.let {
                        dashboardBackgroundUseCase.execute(
                            uri = it,
                            code = this@run.code.value
                        )
                    }

                    if (result.data.posts.isNotEmpty()) {
                        this@run.lovers.filter {
                            it.image.value.isNotNull()
                        }.forEachIndexed { index, it ->
                            it.image.value?.let {
                                dashboardImageLoverUseCase.execute(
                                    uri = it,
                                    code = this@run.code.value,
                                    id = result.data.posts[index].id
                                )
                            }
                        }
                    }

                    screenState.value = ScreenState.ScreenSuccess
                }
                is Result.Failure -> {
                    screenState.value = ScreenState.ScreenContent
                    invalideToast(activity = activity, result.err.message.orEmpty())
                }
            }
        }
    }

    private fun invalideToast(activity: Activity, id: Int) = activity.run {
        Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show();
    }

    private fun invalideToast(activity: Activity, text: String) = activity.run {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    fun onClickInsert() = viewModelScope.launch {
        uiState.screenState.value = ScreenState.ScreenContent
    }

    fun onClickDashboard() = viewModelScope.launch {
        uiState.screenState.value = ScreenState.ScreenDashBoard
    }

    fun onClickAccess() = uiState.code.value.run {
        if (this.isNotEmpty()) {
            input.setCode(this)
            input.setUser(null)
            if (uiState.screenState.value == ScreenState.ScreenSuccess) {
                uiState.screenState.value = ScreenState.ScreenDashBoard
            }
            onClickMenu()
        }
    }

    fun onClickMenu() = viewModelScope.launch {
        sendEvent(ScreenEvent.NavigateTo(Navigation.MenuPopStack))
    }

    fun onClickBackSpace() = uiState.screenState.run {
        if (value != ScreenState.ScreenDashBoard) {
            onClickDashboard()
        }
    }

    sealed class ScreenState {
        object ScreenDashBoard : ScreenState()
        object ScreenLoading : ScreenState()
        object ScreenContent : ScreenState()
        object ScreenSuccess : ScreenState()
        object ScreenError : ScreenState()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        data class NavigateTo(val navigation: Navigation) : ScreenEvent()
    }

    class UiState {
        private val RANGE_LOVERS = 0..4

        private val PERMANET = 0
        private val MINUTO = 1
        private val THIRTY_MINUTO = 30
        private val ONE_HOUR = 60
        private val THREE_HOUR = ONE_HOUR * 3
        private val ONE_DAY = ONE_HOUR * 24
        private val ONE_WEEK = ONE_DAY * 7
        private val TWO_WEEK = ONE_WEEK * 2
        private val ONE_MONTH = ONE_DAY * 30

        val optionsTimeLife = listOf(
            Pair("Permanente", PERMANET),
            Pair("1 minutos", MINUTO),
            Pair("30 minutos", THIRTY_MINUTO),
            Pair("1 hora", ONE_HOUR),
            Pair("3 horas", THREE_HOUR),
            Pair("1 dia", ONE_DAY),
            Pair("1 semana", ONE_WEEK),
            Pair("2 semana", TWO_WEEK),
            Pair("1 mes", ONE_MONTH)
        )

        val screenState = MutableStateFlow<ScreenState>(ScreenState.ScreenDashBoard)

        val code = mutableStateOf(EMPTY_STRING)
        val name = mutableStateOf(EMPTY_STRING)
        val nameLover = mutableStateOf(EMPTY_STRING)
        val plus = mutableStateOf(EMPTY_STRING)
        val linkPlus = mutableStateOf(EMPTY_STRING)
        val socialMediaLink = mutableStateOf(EMPTY_STRING)
        val whatssap = mutableStateOf(EMPTY_STRING)
        val selectedTimeLife = mutableStateOf(optionsTimeLife.first())

        val profileImage = MutableStateFlow<Uri?>(null)
        val backgroundImage = MutableStateFlow<Uri?>(null)
        val validate = MutableStateFlow(EMPTY_STRING)

        val lovers = mutableStateListOf<LoverState>().apply {
            for (i in RANGE_LOVERS) {
                add(LoverState())
            }
        }

        class LoverState {
            val text = mutableStateOf(EMPTY_STRING)
            val link = mutableStateOf(EMPTY_STRING)
            val image = MutableStateFlow<Uri?>(null)
        }
    }
}
