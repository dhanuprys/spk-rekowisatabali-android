package com.dedan.rekowisatabali.ui.screen.home

import PulsatingBackgroundButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.model.PlaceRecommendationHistory
import com.dedan.rekowisatabali.ui.AppViewModel
import com.dedan.rekowisatabali.ui.layout.DataEmpty
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@Composable
fun HomeScreen(
    navigateToCalculationForm: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = AppViewModel.Factory
    )
) {
    val placeRecommendationHistory = viewModel.recommendationHistory.collectAsState()

    Scaffold(
//        topBar = { SpkAppBar(HomeDestination.titleRes) },
        modifier = modifier
    ) { innerPadding ->
        HomeBody(
            placeRecommendationHistory = placeRecommendationHistory.value,
            navigateToCalculationForm = navigateToCalculationForm,
            modifier = modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeBody(
    placeRecommendationHistory: List<PlaceRecommendationHistory>,
    navigateToCalculationForm: () -> Unit,
    modifier: Modifier = Modifier
) {
    var height by remember { mutableStateOf(0) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier.background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_banner),
                alpha = 0.7f,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(190.dp)
            )
        }

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background
            ),
            shape = RoundedCornerShape(
                topStart = 14.dp,
                topEnd = 14.dp,
                bottomStart = 0.dp,
                bottomEnd = 0.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .offset(y = (-20).dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Rekomendasi terakhir",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(16.dp))
                LastRecommendations(
                    placeRecommendationHistory,
                    modifier = Modifier.weight(1f)
                )

                PulsatingBackgroundButton(
                    onClick = navigateToCalculationForm,
                    shape = MaterialTheme.shapes.medium,
                    contentPadding = PaddingValues(15.dp),
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Text("Dapatkan rekomendasi sekarang")
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = null
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun HomeBodyPreview() {
    RekoWisataBaliTheme {
        HomeBody(
            navigateToCalculationForm = {},
            placeRecommendationHistory = emptyList(),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun LastRecommendations(
    placeRecommendationHistory: List<PlaceRecommendationHistory>,
    modifier: Modifier = Modifier
) {
    if (placeRecommendationHistory.isEmpty()) {
        DataEmpty(modifier = modifier.fillMaxWidth())
        return
    }

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(placeRecommendationHistory) { history ->
            TopPlaceCard(
                recommendation = history,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun TopPlaceCard(
    recommendation: PlaceRecommendationHistory,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clickable {}
            .padding(vertical = 8.dp)
    ) {
       Image(
           painter = painterResource(id = R.drawable.home_banner),
           contentDescription = null,
           contentScale = ContentScale.Crop,
           modifier = Modifier
               .height(70.dp)
               .width(70.dp)
               .clip(MaterialTheme.shapes.medium)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = recommendation.name,
                modifier = Modifier
            )
            Text(
                text = recommendation.cityName,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}