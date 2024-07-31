package com.dedan.rekowisatabali.ui.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.ui.screen.info.InfoDestination
import com.dedan.rekowisatabali.ui.screen.home.HomeDestination
import com.dedan.rekowisatabali.ui.screen.tutorial.TutorialDestination

@Composable
fun SpkAppDrawer(
    redirectTo: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    ModalDrawerSheet(modifier = modifier) {
        NavigationDrawerItem(
            icon = {
                Icon(imageVector = Icons.Filled.Home, contentDescription = null)
            },
            label = {
                Text("Beranda")
            },
            selected = true,
            onClick = { redirectTo(HomeDestination.route, true) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            icon = {
                Icon(painter = painterResource(id = R.drawable.ic_tutorial), contentDescription = null)
            },
            label = {
                Text("Tutorial")
            },
            selected = false,
            onClick = { redirectTo(TutorialDestination.route, true) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
        NavigationDrawerItem(
            icon = {
                Icon(imageVector = Icons.Filled.Info, contentDescription = null)
            },
            label = {
                Text("Info Aplikasi")
            },
            selected = false,
            onClick = { redirectTo(InfoDestination.route, true) },
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}