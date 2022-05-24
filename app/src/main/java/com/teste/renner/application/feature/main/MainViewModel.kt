package com.teste.renner.application.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.renner.commons.extensions.EMPTY_STRING
import com.teste.renner.commons.viewModel.ChannelEventSenderImpl
import com.teste.renner.commons.viewModel.EventSender
import com.teste.renner.application.feature.main.MainViewModel.Navigation
import com.teste.renner.commons.extensions.ZERO

class MainViewModel : ViewModel(), EventSender<Navigation> by ChannelEventSenderImpl() {

    var idProduct: Int = ZERO
        private set

    fun setIdProduct(id: Int) {
        this.idProduct = id
    }

    fun startDestination() = Navigation.Menu

    fun navigate(event: Navigation) = viewModelScope.sendEvent(event = event)

    sealed class Navigation(
        val route: String = EMPTY_STRING,
        val popStack: Boolean = false
    ) {
        object Menu : Navigation(route = "menu")
        object Detail : Navigation(route = "detail")
    }
}