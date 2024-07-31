package com.dedan.rekowisatabali.ui.screen.tutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination

object TutorialDestination : NavigationDestination {
    override val route = "tutorial"
    override val titleRes = R.string.app_name
}

@Composable
fun TutorialScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = TutorialDestination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        TutorialBody(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun TutorialBody(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
       Text("Tutorial")
    }
}