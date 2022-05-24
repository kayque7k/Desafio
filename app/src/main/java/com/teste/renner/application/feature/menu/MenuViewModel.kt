package com.teste.renner.application.feature.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.renner.commons.viewModel.ChannelEventSenderImpl
import com.teste.renner.commons.viewModel.EventSender
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import com.teste.renner.application.feature.menu.MenuViewModel.ScreenEvent
import com.teste.renner.application.feature.main.MainViewModel.Navigation
import com.teste.renner.application.feature.menu.mapper.ItemVOMapper.toProductList
import com.teste.renner.application.feature.menu.mapper.MenuVOMapper.toCategoryList
import com.teste.renner.application.feature.menu.viewobject.ItemVO
import com.teste.renner.application.feature.menu.viewobject.MenuVO
import com.teste.renner.application.usecase.CategoryUseCase
import com.teste.renner.application.usecase.ProductCategoryUseCase
import com.teste.renner.commons.extensions.Result

class MenuViewModel(
    private val categoryUseCase: CategoryUseCase,
    private val productCategoryUseCase: ProductCategoryUseCase
) : ViewModel(), EventSender<ScreenEvent> by ChannelEventSenderImpl() {

    val uiState = UiState()

    fun setup() {
        getCategorys()
        getProducts()
    }

    private fun getCategorys() = viewModelScope.launch {
        uiState.run {
            screenState.value = ScreenState.ScreenLoading
            when (val result = categoryUseCase.execute()) {
                is Result.Success -> {
                    listMenu.value = result.data.toCategoryList()
                    screenState.value = ScreenState.ScreenContent
                }
                is Result.Failure -> {
                    screenState.value = ScreenState.ScreenError
                }
            }
        }
    }

    private fun getProducts(idCategory: Int = DEFAULT_CATEGORY) = viewModelScope.launch {
        uiState.run {
            loadingProduct.value = true
            when (val result = productCategoryUseCase.execute(idCategory)) {
                is Result.Success -> {
                    listItens.value = result.data.toProductList()
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

    fun onClickItemCategory(idCategory: Int) = viewModelScope.launch {
        getProducts(idCategory = idCategory)
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

        val listItens = MutableStateFlow<List<ItemVO>>(emptyList())
        val listMenu = MutableStateFlow<List<MenuVO>>(emptyList())
    }

    companion object {
        const val DEFAULT_CATEGORY = 1
    }
}
