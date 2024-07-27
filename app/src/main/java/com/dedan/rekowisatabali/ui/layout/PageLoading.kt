package com.dedan.rekowisatabali.ui.layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme

@Composable
fun PageLoading(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator(modifier = Modifier.size(40.dp))
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Memuat data")
    }
}

@Preview(showSystemUi = true)
@Composable
fun PageLoadingPreview() {
    RekoWisataBaliTheme {
        PageLoading(
            modifier = Modifier.fillMaxSize()
        )
    }
}