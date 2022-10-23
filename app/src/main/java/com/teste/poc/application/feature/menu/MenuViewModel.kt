package com.teste.poc.application.feature.menu

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.theapache64.twyper.TwyperController
import com.github.theapache64.twyper.TwyperControllerImpl
import com.teste.poc.R
import com.teste.poc.application.feature.detail.DetailViewModel
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.application.feature.mapper.ItemVOMapper.toItemVO
import com.teste.poc.application.feature.mapper.ItemVOMapper.toItemVOFilter
import com.teste.poc.application.feature.menu.MenuViewModel.ScreenEvent
import com.teste.poc.application.feature.viewobject.ItemCardVO
import com.teste.poc.application.feature.viewobject.ItemVO
import com.teste.poc.application.usecase.UserUseCase
import com.teste.poc.commons.extensions.Result
import com.teste.poc.commons.extensions.exceptionToast
import com.teste.poc.commons.extensions.isNull
import com.teste.poc.commons.viewModel.ChannelEventSenderImpl
import com.teste.poc.commons.viewModel.EventSender
import com.teste.poc.coreapi.session.ISessionOutput
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class MenuViewModel(
    private val userUseCase: UserUseCase,
    private val output: ISessionOutput
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = UiState()

    fun getPerson(activity: Activity) = uiState.run {
        if (item.value.cardsVO.isNotEmpty() &&
            output.getCode() == item.value.code
        ) {
            val list = item.value.cardsVO.toMutableList()
            item.value.cardsVO.clear()
            item.value.cardsVO = list
            return@run
        }
        viewModelScope.launch {
            loadingProduct.value = true
            when (val result = userUseCase.execute(
                load = {
                    screenState.value = ScreenState.ScreenLoading
                }
            )) {
                is Result.Success -> {
                    if(result.data.isNull()){
                        dashboardPopStack(activity = activity)
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
            loadingProduct.value = false
        }
    }

    fun onClickItem() = viewModelScope.launch {
        sendEvent(ScreenEvent.NavigateTo(Navigation.Detail))
    }

    fun onSwipeRightItem(itemVO: ItemCardVO) = uiState.apply {
        item.value.cardsVO.remove(itemVO)
    }

    fun onSwipeLeftItem(itemVO: ItemCardVO) = uiState.apply {
        twyperController.value.currentCardController?.onDragCancel()
        twyperController.value.currentCardController?.swipeRight()
    }

    fun onEmpty(activity: Activity) = getPerson(activity = activity)

    fun onClickInstagram(activity: Activity) = viewModelScope.launch {
        uiState.item.value.instagram.let {
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
                        exceptionToast(activity, R.string.error_instagram)
                    }
                }
            }
        }
    }

    fun onClickChat(activity: Activity) = viewModelScope.launch {
        try {
            val uri = Uri.parse("$NUMBER_WHATS${uiState.item.value.number}")
            val i = Intent(Intent.ACTION_SENDTO, uri)
            i.setPackage(PACKAGE_WHATS)
            activity.startActivity(Intent.createChooser(i, TITLE_WHATS))
        } catch (ex: Exception) {
            exceptionToast(activity, R.string.error_whatssap)
        }
    }

    fun onClickSpotify(activity: Activity) = viewModelScope.launch {
        try {
            val spotifyIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(uiState.item.value.spotify)
            )
            activity.startActivity(spotifyIntent)
        } catch (ex: Exception) {
            exceptionToast(activity, R.string.error_spotify)
        }
    }

    fun onClickBlock() = viewModelScope.launch {
        uiState.openDialogFavorite.value = true
    }

    fun onClickinsert() = viewModelScope.launch {
        sendEvent(ScreenEvent.NavigateTo(Navigation.Dashboard))
    }

    private fun dashboardPopStack(activity: Activity) = viewModelScope.launch {
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
        val loadingProduct = MutableStateFlow(false)
        val openDialogFavorite = MutableStateFlow(false)

        val twyperController = MutableStateFlow<TwyperController>(TwyperControllerImpl())

        val item = MutableStateFlow(ItemVO())
    }

    companion object {
        private const val TITLE_WHATS = "Adorei esse aplicativo S2"
        private const val NUMBER_WHATS = "smsto:"
        private const val PACKAGE_WHATS = "com.whatsapp"
        private const val PACKAGE_INSTAGRAM = "com.instagram.android"
    }
}
