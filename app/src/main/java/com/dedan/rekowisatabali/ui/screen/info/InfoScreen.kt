package com.dedan.rekowisatabali.ui.screen.info

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination

object InfoDestination : NavigationDestination {
    override val route = "info"
    override val titleRes = R.string.app_name
}

@Composable
fun InfoScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = InfoDestination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        InfoBody(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun InfoBody(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Text("Info Aplikasi")
    }
}