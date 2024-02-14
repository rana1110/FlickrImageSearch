package com.flickrs.imagesearch.ui.commons

import SearchScreen
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.flickrs.imagesearch.domain.entities.MappedImageItemModel
import com.flickrs.imagesearch.ui.searchImage.ImageDetailScreen
import com.flickrs.imagesearch.ui.searchImage.ImageSearchViewModel
import com.flickrs.imagesearch.ui.searchImage.ImageSearchWithDetailTwoPane

@Composable
fun Navigation(
    windowSize: WindowWidthSizeClass
) {
    val viewModel: ImageSearchViewModel = hiltViewModel()
    val replyUiState = viewModel.uiState.collectAsState().value
    val navController = rememberNavController()

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            NavigationBuilder(viewModel, navController)
        }

        WindowWidthSizeClass.Medium -> {
            ImageSearchWithDetailTwoPane(viewModel, replyUiState)
        }

        WindowWidthSizeClass.Expanded -> {
            ImageSearchWithDetailTwoPane(viewModel, replyUiState)
        }

        else -> {
            NavigationBuilder(viewModel, navController)

        }
    }
}

@Composable
fun NavigationBuilder(viewModel: ImageSearchViewModel, navController: NavHostController) {

    NavHost(navController = navController, startDestination = Destinations.Home.path) {
        composable(Destinations.Home.path) {
            SearchScreen(viewModel, onImageClicked = { imageItem ->
                navController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "imageItem",
                    value = imageItem
                )
                navController.navigate(Destinations.Details.path)
            })
        }

        composable(Destinations.Details.path) {
            val result =
                navController.previousBackStackEntry?.savedStateHandle?.get<MappedImageItemModel>("imageItem")
            result?.let { it1 ->
                ImageDetailScreen(result = it1) {
                    navController.navigateUp()
                }
            }
        }

    }
}

sealed class Destinations(val path: String) {
    object Home : Destinations("home")
    object Details : Destinations("details")
}
