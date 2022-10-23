package com.teste.poc.application.feature.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.teste.poc.R
import com.teste.poc.application.feature.dashboard.DashboardPageScreen
import com.teste.poc.application.feature.dashboard.DashboardViewModel
import com.teste.poc.application.feature.detail.DetailPageScreen
import com.teste.poc.application.feature.detail.DetailViewModel
import com.teste.poc.commons.navigartion.setNavigationContent
import com.teste.poc.application.feature.main.MainViewModel.Navigation.Detail
import com.teste.poc.application.feature.main.MainViewModel.Navigation.Menu
import com.teste.poc.application.feature.main.MainViewModel.Navigation.Dashboard
import com.teste.poc.application.feature.main.MainViewModel.Navigation
import com.teste.poc.application.feature.menu.MenuPageScreen
import com.teste.poc.application.feature.menu.MenuViewModel
import com.teste.poc.commons.navigartion.composeNavigate
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val _flowViewModel: MainViewModel by inject()

    private val _menuViewModel: MenuViewModel by inject()

    private val _dashboardViewModel: DashboardViewModel by inject()

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
        composable(Dashboard.route) {
            DashboardPageScreen(
                viewModel = _dashboardViewModel,
                flowViewModel = _flowViewModel
            )
        }
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