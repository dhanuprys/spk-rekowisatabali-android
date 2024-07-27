package com.dedan.rekowisatabali.ui.screen.calculationform.result

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dedan.rekowisatabali.model.PlaceRecommendation
import com.dedan.rekowisatabali.ui.AppViewModel
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme
import com.dedan.rekowisatabali.util.remap
import com.dedan.rekowisatabali.ui.screen.calculationform.ResultUiState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.ui.layout.DataEmpty
import com.dedan.rekowisatabali.ui.layout.PageError
import com.dedan.rekowisatabali.ui.layout.PageLoading
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.ReportOptionsUiState
import kotlinx.coroutines.launch

object ResultDestination : NavigationDestination {
    override val route = "calculationform_result"
    override val titleRes = R.string.recommendation_result
}

@Composable
fun ResultScreen(
    viewModel: CalculationFormViewModel,
    navigateUp: () -> Unit,
    backToHome: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resultUiState = viewModel.resultUiState
    val reportOptionsUiState = viewModel.reportOptionsUiState

    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = ResultDestination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        bottomBar = { BackToHomeButton(onClick = backToHome) },
        modifier = modifier
    ) { innerPadding ->
        ResultBody(
            uiState = resultUiState,
            optionsUiState = reportOptionsUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun BackToHomeButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    BottomAppBar(modifier = modifier) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Kembali ke beranda")
        }
    }
}

@Composable
fun ResultBody(
    uiState: ResultUiState,
    optionsUiState: ReportOptionsUiState,
    modifier: Modifier = Modifier
) {
    when (uiState) {
        is ResultUiState.Loading ->
            PageLoading(modifier = Modifier.fillMaxSize())

        is ResultUiState.Error ->
            PageError(modifier = Modifier.fillMaxSize())

        is ResultUiState.Success -> {
            if (uiState.data.isEmpty()) {
                DataEmpty(modifier = modifier.fillMaxSize())
            } else {
                ResultList(
                    withGraphReport = optionsUiState.withGraphReport,
                    withTableReport = optionsUiState.withTableReport,
                    recommendations = uiState.data,
                    modifier = modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ResultBodyPreview() {
    RekoWisataBaliTheme {
        ResultBody(
            uiState = ResultUiState.Success(
                listOf(
                    PlaceRecommendation(1, "Hello 1", 1, 0.9f)
                )
            ),
            optionsUiState = ReportOptionsUiState(),
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun ResultList(
    withGraphReport: Boolean,
    withTableReport: Boolean,
    recommendations: List<PlaceRecommendation>,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    var orderByName by remember { mutableStateOf<Boolean>(false) }
    val orderedRecommendations: List<PlaceRecommendation> by remember(orderByName, recommendations) {
        derivedStateOf {
            if (orderByName)
                recommendations.sortedBy { it.name }
            else
                recommendations
        }
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        ItemOrderList(
            isOrderByName = orderByName,
            onOrderByName = { state ->
                coroutineScope.launch {
                    orderByName = state
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        if (withGraphReport) {
            Spacer(modifier = Modifier.height(40.dp))
            GraphReportView(recommendations = orderedRecommendations)
        }

        if (withTableReport) {
            Spacer(modifier = Modifier.height(40.dp))
            TableReportView(recommendations = orderedRecommendations)
        }
    }
}

@Composable
fun ItemOrderList(
    isOrderByName: Boolean,
    onOrderByName: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column {
            ItemOrderRadio(
                text = "Urutkan berdasarkan peringkat",
                selected = !isOrderByName,
                onClick = { onOrderByName(false) },
                modifier = Modifier.fillMaxWidth()
            )
            ItemOrderRadio(
                text = "Urutkan berdasarkan nama",
                selected = isOrderByName,
                onClick = { onOrderByName(true) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun ItemOrderListPreview() {
    RekoWisataBaliTheme {
        ItemOrderList(
            isOrderByName = false,
            onOrderByName = {}
        )
    }
}

@Composable
fun ItemOrderRadio(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onClick() }
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = text)
    }
}

@Preview
@Composable
fun ItemOrderRadioPreview() {
    RekoWisataBaliTheme {
        ItemOrderRadio(
            text = "Urutkan berdasarkan peringkat",
            selected = true,
            onClick = { /*TODO*/ }
        )
    }
}

@Composable
fun GraphReportView(
    recommendations: List<PlaceRecommendation>,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "Daftar rekomendasi (grafik)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier
        ) {
            recommendations.forEach { recommendation ->
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = recommendation.name,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    BoxWithConstraints {
                        val graphWidth = remap(
                            recommendation.rank.toFloat() - 1,
                            recommendations.size.toFloat(),
                            0f,
                            0f,
                            maxWidth.value
                        )

                        Box(
                            modifier = Modifier
                                .height(16.dp)
                                .width(graphWidth.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp)
                                )
                        )
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GraphReportViewPreview() {
    val recommendations = listOf(
        PlaceRecommendation(1, "Hello 1", 1, 0.999f),
        PlaceRecommendation(1, "Hello 2", 4, 0.999f),
        PlaceRecommendation(1, "Hello 3", 3, 0.999f),
        PlaceRecommendation(1, "Hello 4", 2, 0.999f),
        PlaceRecommendation(1, "Hello 4", 5, 0.999f),
        PlaceRecommendation(1, "Hello 4", 6, 0.999f)
    )

    RekoWisataBaliTheme {
        GraphReportView(
            recommendations = recommendations
        )
    }
}

@Composable
fun TableReportView(
    recommendations: List<PlaceRecommendation>,
    modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = "Daftar rekomendasi (tabel)",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = modifier
        ) {
            recommendations.forEach {
                Row(
                    modifier = Modifier
                        .border(1.dp, MaterialTheme.colorScheme.primary)
                        .fillMaxWidth()
                        .height(40.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .border(1.dp, MaterialTheme.colorScheme.primary)
                            .fillMaxHeight()
                            .padding(8.dp)
                            .widthIn(max = 25.dp)
                    ) {
                        Text(
                            text = it.rank.toString(),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .horizontalScroll(rememberScrollState())
                    ) {
                        Text(
                            text = it.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}