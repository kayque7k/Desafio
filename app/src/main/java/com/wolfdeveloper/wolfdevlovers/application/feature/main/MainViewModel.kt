package com.wolfdeveloper.wolfdevlovers.application.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.ChannelEventSenderImpl
import com.wolfdeveloper.wolfdevlovers.commons.viewModel.EventSender
import com.wolfdeveloper.wolfdevlovers.application.feature.main.MainViewModel.Navigation
import com.wolfdeveloper.wolfdevlovers.commons.extensions.ZERO
import com.wolfdeveloper.wolfdevlovers.commons.extensions.isNull
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessionOutput

class MainViewModel() : ViewModel(), EventSender<Navigation> by ChannelEventSenderImpl() {

    var idLover: Int = ZERO
        private set

    fun setIdLover(id: Int) {
        this.idLover = id
    }

    fun startDestination() = Navigation.Menu

    fun navigate(event: Navigation) = viewModelScope.sendEvent(event = event)

    sealed class Navigation(
        val route: String = EMPTY_STRING,
        val popStack: Boolean = false
    ) {
        object Dashboard : Navigation(route = "dashboard")
        object Menu : Navigation(route = "menu")
        object Detail : Navigation(route = "detail")
        object MenuPopStack : Navigation(route = "menu", popStack = true)
        object DashboardPopStack : Navigation(route = "dashboard", popStack = true)
    }
}