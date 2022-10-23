package com.teste.poc.application.feature.menu

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.theapache64.twyper.TwyperController
import com.github.theapache64.twyper.TwyperControllerImpl
import com.teste.poc.commons.viewModel.ChannelEventSenderImpl
import com.teste.poc.commons.viewModel.EventSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.teste.poc.application.feature.menu.MenuViewModel.ScreenEvent
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.application.feature.mapper.ItemVOMapper.toItemVO
import com.teste.poc.application.feature.viewobject.ItemCardVO
import com.teste.poc.application.feature.viewobject.ItemVO
import com.teste.poc.application.usecase.UserUseCase
import com.teste.poc.commons.extensions.Result

class MenuViewModel(
    private val youPersonUseCase: UserUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = UiState()

    fun getPerson() = uiState.run {
        if (item.value.cardsVO.isNotEmpty()) {
            val list = item.value.cardsVO.toMutableList()
            item.value.cardsVO.clear()
            item.value.cardsVO = list
            return@run
        }
        viewModelScope.launch {
            loadingProduct.value = true
            when (val result = youPersonUseCase.execute()) {
                is Result.Success -> {
                    item.value = result.data.toItemVO()
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

    fun onEmpty() = getPerson()

    fun onClickChat(activity: Activity) = viewModelScope.launch {
        val uri = Uri.parse("$NUMBER_WHATS${uiState.item.value.number}")
        val i = Intent(Intent.ACTION_SENDTO, uri)
        i.setPackage(PACKAGE_WHATS)
        activity.startActivity(Intent.createChooser(i, TITLE_WHATS))
    }

    fun onClickSpotify(activity: Activity) = viewModelScope.launch {
        val spotifyIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse(uiState.item.value.spotify)
        )
        activity.startActivity(spotifyIntent)
    }

    fun onClickBlock() = viewModelScope.launch {
        uiState.openDialogFavorite.value = true
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
    }
}
