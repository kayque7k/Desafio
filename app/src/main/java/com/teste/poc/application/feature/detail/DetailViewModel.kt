package com.teste.poc.application.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.poc.commons.viewModel.ChannelEventSenderImpl
import com.teste.poc.commons.viewModel.EventSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.teste.poc.application.feature.detail.DetailViewModel.ScreenEvent
import com.teste.poc.application.feature.detail.mapper.ItemVOMapper.toProduct
import com.teste.poc.application.feature.detail.viewobject.ItemVO
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.application.usecase.ProductUseCase
import com.teste.poc.commons.extensions.Result

class DetailViewModel(
    private val productUseCase: ProductUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {


    val uiState = UiState()

    fun onClickBack() = viewModelScope.launch {
        sendEvent(event = ScreenEvent.GoBack)
    }

    fun getDetails(id: Int) = viewModelScope.launch {
        uiState.run {
            screenState.value = ScreenState.ScreenLoading
            when (val result = productUseCase.execute(
                id = id
            )) {
                is Result.Success -> {
                    item.value = result.data.toProduct()
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

        val item = MutableStateFlow<ItemVO>(ItemVO())
    }

}
