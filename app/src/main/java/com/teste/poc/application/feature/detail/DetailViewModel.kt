package com.teste.poc.application.feature.detail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.poc.application.feature.detail.DetailViewModel.ScreenEvent
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.application.feature.mapper.ItemVOMapper.toItemVOFilter
import com.teste.poc.application.feature.viewobject.ItemVO
import com.teste.poc.application.usecase.DetailsPersonUseCase
import com.teste.poc.commons.extensions.Result
import com.teste.poc.commons.extensions.startServiceVerify
import com.teste.poc.commons.viewModel.ChannelEventSenderImpl
import com.teste.poc.commons.viewModel.EventSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val personUseCase: DetailsPersonUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    companion object {
        private const val APP_YOUTUBE = "vnd.youtube:"
        private const val WEB_YOUTUBE = "http://www.youtube.com/watch?v="
    }

    val uiState = UiState()

    fun onClickBack() = viewModelScope.launch {
        sendEvent(event = ScreenEvent.GoBack)
    }

    fun onClickMusic(activity: Activity, url: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("$APP_YOUTUBE$url"))
        val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse("$WEB_YOUTUBE$url"))
        try {
            activity.startActivity(intentApp)
        } catch (ex: ActivityNotFoundException) {
            activity.startActivity(intentBrowser)
        }
    }

    fun getDetails(activity: Activity, id: Int) = viewModelScope.launch {
        uiState.run {
            screenState.value = ScreenState.ScreenLoading
            when (val result = personUseCase.execute()) {
                is Result.Success -> {
                    item.value = result.data.toItemVOFilter(id)
                    screenState.value = ScreenState.ScreenContent
                }
                is Result.Failure -> {
                    screenState.value = ScreenState.ScreenError
                }
            }
        }
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
