package com.teste.poc.application.feature.detail

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.poc.R
import com.teste.poc.application.feature.detail.DetailViewModel.ScreenEvent
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.application.feature.mapper.ItemVOMapper.toItemVOFilter
import com.teste.poc.application.feature.viewobject.ItemVO
import com.teste.poc.application.usecase.DetailsUserUseCase
import com.teste.poc.commons.extensions.Result
import com.teste.poc.commons.extensions.exceptionToast
import com.teste.poc.commons.viewModel.ChannelEventSenderImpl
import com.teste.poc.commons.viewModel.EventSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val detailsUserUseCase: DetailsUserUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    companion object {
        private const val EMOJI = "\uD83E\uDD70"
        private const val APP_YOUTUBE = "vnd.youtube:"
    }

    val uiState = UiState()

    fun onClickBack() = viewModelScope.launch {
        sendEvent(event = ScreenEvent.GoBack)
    }

    fun onClickMusic(activity: Activity, url: String) {
        val intentApp = Intent(Intent.ACTION_VIEW, Uri.parse("$APP_YOUTUBE$url"))
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

    fun getDetails(id: Int) = viewModelScope.launch {
        uiState.run {
            screenState.value = ScreenState.ScreenLoading
            when (val result = detailsUserUseCase.execute()) {
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
