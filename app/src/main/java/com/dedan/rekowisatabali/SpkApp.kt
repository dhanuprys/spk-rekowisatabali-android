package com.dedan.rekowisatabali

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dedan.rekowisatabali.ui.navigation.SpkNavHost

@Composable
fun SpkApp(
    navController: NavHostController = rememberNavController()
) {
    SpkNavHost(navController = navController);
}