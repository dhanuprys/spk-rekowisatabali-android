package com.dedan.rekowisatabali.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToCalculationForm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { SpkAppBar(HomeDestination.titleRes) },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            navigateToCalculationForm = navigateToCalculationForm,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    navigateToCalculationForm: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Button(onClick = navigateToCalculationForm) {
            Text("Kalkulasi")
        }
    }
}