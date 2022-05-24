package com.teste.renner.application.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.teste.renner.application.feature.detail.DetailPageScreen
import com.teste.renner.application.feature.detail.DetailViewModel
import com.teste.renner.commons.navigartion.composeNavigate
import com.teste.renner.commons.navigartion.setNavigationContent
import com.teste.renner.application.feature.main.MainViewModel.Navigation.Detail
import com.teste.renner.application.feature.main.MainViewModel.Navigation.Menu
import com.teste.renner.application.feature.main.MainViewModel.Navigation
import com.teste.renner.application.feature.menu.MenuPageScreen
import com.teste.renner.application.feature.menu.MenuViewModel
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val _flowViewModel: MainViewModel by inject()

    private val _menuViewModel: MenuViewModel by inject()

    private val _detailViewModel: DetailViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _flowViewModel.run {
            setNavigationContent(
                startDestination = startDestination().route,
                navGraphBuilder = ::navGraphBuilder,
                navEventFlow = eventsFlow,
                navEvent = ::navEvent
            )
        }
    }

    private fun navGraphBuilder(builder: NavGraphBuilder) = builder.apply {
        composable(Menu.route) {
            MenuPageScreen(
                viewModel = _menuViewModel,
                flowViewModel = _flowViewModel
            )
        }
        composable(Detail.route) {
            DetailPageScreen(
                viewModel = _detailViewModel,
                flowViewModel = _flowViewModel
            )
        }
    }

    private fun navEvent(navController: NavHostController, navScreen: Navigation) {
        navController.composeNavigate(
            route = navScreen.route,
            popStack = navScreen.popStack
        )
    }
}