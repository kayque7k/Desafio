package com.wolfdeveloper.wolfdevlovers.application.feature.detail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.application.feature.detail.DetailViewModel.ScreenEvent
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel.Navigation
import com.wolfdeveloper.wolfdevlovers.application.feature.mapper.ItemVOMapper.toItemVOFilter
import com.wolfdeveloper.wolfdevlovers.application.feature.viewobject.ItemVO
import com.wolfdeveloper.wolfdevlovers.application.usecase.DetailsUserUseCase
import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.Result
import com.wolfdeveloper.wolfdevlovers.commons.extensions.exceptionToast
import com.wolfdeveloper.wolfdevlovers.commons.extensions.isNull
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.ChannelEventSenderImpl
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.EventSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailsUserUseCase: DetailsUserUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    companion object {
        private const val EMOJI = "\uD83E\uDD70"
        private const val APP_YOUTUBE = "vnd.youtube:"
        private const val URL_YOUTUBE = "https://www.youtube.com/watch?v="
    }

    val uiState = UiState()

    fun onClickBack() = viewModelScope.launch {
        sendEvent(event = ScreenEvent.GoBack)
    }

    fun onClickMusic(activity: Activity, url: String) {
        val intentApp = Intent(
            Intent.ACTION_VIEW, Uri.parse(
                "$APP_YOUTUBE${
                    url.replace(
                        URL_YOUTUBE, EMPTY_STRING
                    )
                }"
            )
        )
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        try {
            Toast.makeText(
                activity,
                activity.getString(R.string.notify_text, EMOJI),
                Toast.LENGTH_LONG
            ).show();
            activity.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            try {
                activity.startActivity(intentBrowser)
            } catch (ex: Exception) {
                exceptionToast(activity, R.string.error_youtube)
            }
        }
    }

    fun getDetails(activity: Activity, id: Int) = viewModelScope.launch {
        uiState.run {
            screenState.value = ScreenState.ScreenLoading
            when (val result = detailsUserUseCase.execute(
                load = {
                    screenState.value = ScreenState.ScreenLoading
                }
            )) {
                is Result.Success -> {
                    if (result.data.isNull()) {
                        dashboardPopStack(activity = activity)
                    } else {
                        result.data?.let {
                            item.value = it.toItemVOFilter(id)
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

    fun dashboardPopStack(activity: Activity) = viewModelScope.launch {
        exceptionToast(activity, R.string.error_code)
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

    class UiState {
        val screenState = MutableStateFlow<ScreenState>(ScreenState.ScreenContent)
        val item = MutableStateFlow(ItemVO())
    }

}
