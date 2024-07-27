package com.dedan.rekowisatabali.ui.screen.calculationform.step3

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.ui.AppViewModel
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.ReportOptionsUiState
import com.dedan.rekowisatabali.ui.screen.calculationform.ResultUiState
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme

object Step3Destination : NavigationDestination {
    override val route = "calculationform_step3"
    override val titleRes = R.string.select_report
}

@Composable
fun Step3Screen(
    viewModel: CalculationFormViewModel,
    navigateUp: () -> Unit,
    navigateToResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.reportOptionsUiState

    BackHandler {
        viewModel.resetCriteria()
        navigateUp()
    }

    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = Step3Destination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Step3Body(
            uiState = uiState,
            onLimitChange = viewModel::setReportLimit,
            onGraphReportChange = viewModel::setGraphReport,
            onTableReportChange = viewModel::setTableReport,
            navigateToResult = navigateToResult,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        )
    }
}

@Composable
fun Step3Body(
    uiState: ReportOptionsUiState,
    onLimitChange: (Int) -> Unit,
    onGraphReportChange: (Boolean) -> Unit,
    onTableReportChange: (Boolean) -> Unit,
    navigateToResult: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            RecommendationLimitSelectList(
                selectedLimit = uiState.recommendationLimit,
                onLimitChange = onLimitChange
            )
            Spacer(modifier = Modifier.padding(16.dp))
            ReportTypeSelectList(
                withGraphReport = uiState.withGraphReport,
                withTableReport = uiState.withTableReport,
                onGraphReportChange = onGraphReportChange,
                onTableReportChange = onTableReportChange,
            )
        }

        Button(
            onClick = navigateToResult,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Dapatkan rekomendasi")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Step3BodyPreview() {
    RekoWisataBaliTheme {
        Step3Body(
            uiState = ReportOptionsUiState(),
            onLimitChange = {},
            onGraphReportChange = {},
            onTableReportChange = {},
            navigateToResult = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun RecommendationLimitSelectList(
    selectedLimit: Int,
    onLimitChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Jumlah hasil maksimal",
            style = MaterialTheme.typography.titleMedium
        )
        Column {
            LimitRadio(
                limit = 5,
                selected = selectedLimit == 5,
                onClick = { onLimitChange(5) },
                modifier = Modifier.fillMaxWidth()
            )
            LimitRadio(
                limit = 10,
                selected = selectedLimit == 10,
                onClick = { onLimitChange(10) },
                modifier = Modifier.fillMaxWidth()
            )
            LimitRadio(
                limit = 15,
                selected = selectedLimit == 15,
                onClick = { onLimitChange(15) },
                modifier = Modifier.fillMaxWidth()
            )
            LimitRadio(
                limit = 20,
                selected = selectedLimit == 20,
                onClick = { onLimitChange(20) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun RecommendationLimitSelectListPreview() {
    RekoWisataBaliTheme {
        RecommendationLimitSelectList(selectedLimit = 5, onLimitChange = {})
    }
}

@Composable
fun LimitRadio(
    limit: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable {
            onClick()
        }
    ) {
        RadioButton(selected = selected, onClick = onClick)
        Text(text = limit.toString())
    }
}

@Preview
@Composable
fun LimitRadioPreview() {
    RekoWisataBaliTheme {
        LimitRadio(limit = 5, selected = true, onClick = { /*TODO*/ })
    }
}

@Composable
fun ReportTypeSelectList(
    withGraphReport: Boolean,
    withTableReport: Boolean,
    onGraphReportChange: (Boolean) -> Unit,
    onTableReportChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Tampilkan dalam bentuk",
            style = MaterialTheme.typography.titleMedium
        )
        Column {
            ReportTypeSelect(
                checked = withGraphReport,
                reportType = "Grafik",
                onCheckedChange = onGraphReportChange,
                modifier = Modifier.fillMaxWidth()
            )
            ReportTypeSelect(
                checked = withTableReport,
                reportType = "Tabel",
                onCheckedChange = onTableReportChange,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview
@Composable
fun ReportTypeSelectListPreview() {
    RekoWisataBaliTheme {
        ReportTypeSelectList(
            withGraphReport = true,
            withTableReport = false,
            onGraphReportChange = {},
            onTableReportChange = {}
        )
    }
}

@Composable
fun ReportTypeSelect(
    checked: Boolean,
    reportType: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!checked) }
    ) {
        Checkbox(checked = checked, onCheckedChange = onCheckedChange)
        Text(text = reportType)
    }
}

@Preview
@Composable
fun ReportTypeSelectPreview() {
    RekoWisataBaliTheme {
        ReportTypeSelect(checked = true, reportType = "Tabel", onCheckedChange = {})
    }
}
