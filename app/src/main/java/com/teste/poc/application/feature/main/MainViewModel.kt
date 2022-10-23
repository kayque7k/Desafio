package com.teste.poc.application.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.teste.poc.commons.extensions.EMPTY_STRING
import com.teste.poc.commons.viewModel.ChannelEventSenderImpl
import com.teste.poc.commons.viewModel.EventSender
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.commons.extensions.ZERO
import com.teste.poc.commons.extensions.isNull
import com.teste.poc.coreapi.session.ISessionOutput

class MainViewModel(
    private val output: ISessionOutput
) : ViewModel(), EventSender<Navigation> by ChannelEventSenderImpl() {

    var idLover: Int = ZERO
        private set

    fun setIdLover(id: Int) {
        this.idLover = id
    }

    fun startDestination() =
        if (output.getUser().isNull()) Navigation.Dashboard else Navigation.Menu

    fun navigate(event: Navigation) = viewModelScope.sendEvent(event = event)

    sealed class Navigation(
        val route: String = EMPTY_STRING,
        val popStack: Boolean = false
    ) {
        object Dashboard : Navigation(route = "dashboard")
        object Menu : Navigation(route = "menu")
        object Detail : Navigation(route = "detail")
    }
}