package com.dedan.rekowisatabali

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.dedan.rekowisatabali.ui.layout.SpkAppDrawer
import com.dedan.rekowisatabali.ui.navigation.SpkNavHost
import kotlinx.coroutines.launch

@Composable
fun SpkApp(
    navController: NavHostController = rememberNavController()
) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            SpkAppDrawer(
                redirectTo = { route, autoClose ->
                    if (autoClose) {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                    }

                    navController.navigate(route)
                }
            )
        }
    ) {
        SpkNavHost(
            navController = navController,
            onDrawerOpenRequest = {
                coroutineScope.launch {
                    drawerState.open()
                }
            }
        )
    }
}