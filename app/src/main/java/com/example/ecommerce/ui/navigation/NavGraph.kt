package com.example.ecommerce.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ecommerce.ui.view.screens.ProductDetailsScreen
import com.example.ecommerce.ui.viewmodel.ProductViewModel

sealed class Screen(val route: String) {
    object ProductDetails : Screen("product_details")
}

@Composable
fun NavGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = Screen.ProductDetails.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.ProductDetails.route) {
            val viewModel: ProductViewModel = hiltViewModel()
            ProductDetailsScreen(
                viewModel = viewModel
            )
        }
    }
}
