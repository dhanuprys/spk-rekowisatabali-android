package com.dedan.rekowisatabali

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RekoWisataBaliTheme {
                SpkApp()
            }
        }
    }
}