package com.dedan.rekowisatabali.ui.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.dedan.rekowisatabali.ui.AppViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.result.ResultDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.result.ResultScreen
import com.dedan.rekowisatabali.ui.screen.calculationform.step1.Step1Destination
import com.dedan.rekowisatabali.ui.screen.calculationform.step1.Step1Screen
import com.dedan.rekowisatabali.ui.screen.calculationform.step2a.Step2ADestination
import com.dedan.rekowisatabali.ui.screen.calculationform.step2a.Step2AScreen
import com.dedan.rekowisatabali.ui.screen.calculationform.step2b.Step2BDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.step2b.Step2BScreen
import com.dedan.rekowisatabali.ui.screen.calculationform.step3.Step3Destination
import com.dedan.rekowisatabali.ui.screen.calculationform.step3.Step3Screen
import com.dedan.rekowisatabali.ui.screen.home.HomeDestination
import com.dedan.rekowisatabali.ui.screen.home.HomeScreen

@Composable
fun SpkNavHost(
    navController: NavHostController
) {
    val calculationFormViewModel: CalculationFormViewModel = viewModel(
        factory = AppViewModel.Factory
    )

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(
            route = HomeDestination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            HomeScreen(
                navigateToCalculationForm = {
                    navController.navigate(Step1Destination.route)
                    calculationFormViewModel.getCities()
                    calculationFormViewModel.getRecommendationTemplates()
                }
            )
        }

        composable(
            route = Step1Destination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            Step1Screen(
                viewModel = calculationFormViewModel,
                navigateUp = {
                    calculationFormViewModel.resetCalculation()
                    navController.navigateUp()
                },
                navigateToStep2A = { navController.navigate(Step2ADestination.route) },
                navigateToStep2B = { navController.navigate(Step2BDestination.route) }
            )
        }

        composable(
            route = Step2ADestination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            Step2AScreen(
                viewModel = calculationFormViewModel,
                navigateUp = { navController.navigateUp() },
                navigateToStep3 = { navController.navigate(Step3Destination.route) }
            )
        }

        composable(
            route = Step2BDestination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            Step2BScreen(
                viewModel = calculationFormViewModel,
                navigateUp = { navController.navigateUp() },
                navigateToStep3 = { navController.navigate(Step3Destination.route) }
            )
        }

        composable(
            route = Step3Destination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            Step3Screen(
                viewModel = calculationFormViewModel,
                navigateUp = {
                    calculationFormViewModel.resetCriteria()
                    navController.navigateUp()
                },
                navigateToResult = {
                    calculationFormViewModel.getResult()
                    navController.navigate(ResultDestination.route)
                }
            )
        }

        composable(
            route = ResultDestination.route,
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None }
        ) {
            ResultScreen(
                viewModel = calculationFormViewModel,
                navigateUp = { navController.navigateUp() },
                backToHome = {
                    navController.navigate(HomeDestination.route)
                    calculationFormViewModel.resetCalculation()
                }
            )
        }
    }
}