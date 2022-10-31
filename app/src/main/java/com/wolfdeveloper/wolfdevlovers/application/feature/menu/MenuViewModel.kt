package com.wolfdeveloper.wolfdevlovers.application.feature.menu

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel.Navigation
import com.wolfdeveloper.wolfdevlovers.application.feature.mapper.ItemVOMapper.toItemVO
import com.wolfdeveloper.wolfdevlovers.application.feature.menu.MenuViewModel.ScreenEvent
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemVO
import com.wolfdeveloper.wolfdevlovers.application.usecase.UserUseCase
import com.wolfdeveloper.wolfdevlovers.commons.extensions.*
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.ChannelEventSenderImpl
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.EventSender
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessionOutput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MenuViewModel(
    private val userUseCase: UserUseCase,
    private val output: ISessionOutput
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    private var attemptsGetCode: Int = ATTEMPTS_GET_CODE
    val uiState = UiState()

    fun getUser(activity: Activity) = uiState.run {
        viewModelScope.launch {
            screenState.value = ScreenState.ScreenLoading
            if(output.getCode().isEmpty()){
                dashboardPopStack()
            }
            when (val result = userUseCase.execute()) {
                is Result.Success -> {
                    if (result.data.isNull()) {
                        exceptionToast(activity, R.string.error_code)
                        dashboardPopStack()
                    } else {
                        result.data?.let {
                            item.value = it.toItemVO()
                        }
                    }
                    screenState.value = ScreenState.ScreenContent
                }
                is Result.Failure -> {
                    screenState.value = ScreenState.ScreenError
                }
            }
        }
    }

    fun onRetry(activity: Activity) = attemptsGetCode.run {
        if (attemptsGetCode.isPositive()) {
            attemptsGetCode--
            getUser(activity = activity)
        } else {
            exceptionToast(activity, R.string.error_code)
            dashboardPopStack()
        }
    }

    fun onClickLink(activity: Activity, url: String) = openLink(activity, url)

    fun onClickItem() = viewModelScope.launch {
        sendEvent(ScreenEvent.NavigateTo(Navigation.Detail))
    }

    fun onClickInstagram(activity: Activity) = viewModelScope.launch {
        uiState.item.value.socialMediaLink.let {
            if (it.isNotEmpty()) {
                try {
                    activity.startActivity(
                        Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(it)
                        ).apply {
                            setPackage(PACKAGE_INSTAGRAM)
                        })
                } catch (e: ActivityNotFoundException) {
                    try {
                        activity.startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse(it)
                            )
                        )
                    } catch (ex: Exception) {
                        exceptionToast(activity, R.string.error_link)
                    }
                }
            }
        }
    }

    fun onClickChat(activity: Activity) = viewModelScope.launch {
        try {
            val uri =
                Uri.parse(
                    "$NUMBER_WHATS${
                        uiState.item.value.number
                            .replace(" ", EMPTY_STRING)
                            .replace("(", EMPTY_STRING)
                            .replace(")", EMPTY_STRING)
                            .replace("-", EMPTY_STRING)
                    }"
                )
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage(PACKAGE_WHATS)
            activity.startActivity(Intent.createChooser(i, TITLE_WHATS))
        } catch (ex: Exception) {
            exceptionToast(activity, R.string.error_whatssap)
        }
    }

    fun onClickAccess(activity: Activity) = openLink(activity, uiState.item.value.linkPlus)

    fun onClickBlock() = viewModelScope.launch {
        uiState.openDialogFavorite.value = true
    }

    fun onClickinsert() = viewModelScope.launch {
        sendEvent(ScreenEvent.NavigateTo(Navigation.Dashboard))
    }

    private fun dashboardPopStack() = viewModelScope.launch {
        sendEvent(event = ScreenEvent.NavigateTo(Navigation.DashboardPopStack))
    }

    sealed class ScreenState {
        object ScreenLoading : ScreenState()
        object ScreenContent : ScreenState()
        object ScreenError : ScreenState()
    }

    sealed class ScreenEvent {
        object GoBack : ScreenEvent()
        data class NavigateTo(val navigation: Navigation) : ScreenEvent()
    }

    data class UiState(
        val screenState: MutableStateFlow<ScreenState> = MutableStateFlow(ScreenState.ScreenContent),
        val openDialogFavorite: MutableStateFlow<Boolean> = MutableStateFlow(false),
        val item: MutableStateFlow<ItemVO> = MutableStateFlow(ItemVO())
    )

    companion object {
        private var ATTEMPTS_GET_CODE = 2

        private const val TITLE_WHATS = "Adorei esse aplicativo S2"
        private const val NUMBER_WHATS = "smsto"
        private const val PACKAGE_WHATS = "com.whatsapp"
        private const val PACKAGE_INSTAGRAM = "com.instagram.android"
    }
}
