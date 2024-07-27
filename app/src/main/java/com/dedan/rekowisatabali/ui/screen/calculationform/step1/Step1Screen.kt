package com.dedan.rekowisatabali.ui.screen.calculationform.step1

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dedan.rekowisatabali.R
import com.dedan.rekowisatabali.model.City
import com.dedan.rekowisatabali.ui.AppViewModel
import com.dedan.rekowisatabali.ui.layout.PageLoading
import com.dedan.rekowisatabali.ui.layout.SpkAppBar
import com.dedan.rekowisatabali.ui.navigation.NavigationDestination
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.calculationform.CityUiState
import com.dedan.rekowisatabali.ui.theme.RekoWisataBaliTheme

object Step1Destination : NavigationDestination {
    override val route = "calculationform_step1"
    override val titleRes = R.string.select_city
}

@Composable
fun Step1Screen(
    viewModel: CalculationFormViewModel,
    navigateUp: () -> Unit,
    navigateToStep2A: () -> Unit,
    navigateToStep2B: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.cityUiState
    val selectedCities = viewModel.selectedCities

    Scaffold(
        topBar = {
            SpkAppBar(
                titleRes = Step1Destination.titleRes,
                canNavigateBack = true,
                onNavigateUp = navigateUp
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Step1Body(
            navigateToStep2A = {
                if (selectedCities.isNotEmpty()) {
                    navigateToStep2A()
                }
            },
            navigateToStep2B = {
                if (selectedCities.isNotEmpty()) {
                    navigateToStep2B()
                }
            },
            uiState = uiState,
            selectedCities = selectedCities,
            toggleCityId = viewModel::toggleCityId,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun Step1Body(
    navigateToStep2A: () -> Unit,
    navigateToStep2B: () -> Unit,
    uiState: CityUiState,
    selectedCities: Set<Int>,
    toggleCityId: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            when (uiState) {
                is CityUiState.Error ->
                    Text("Error")

                is CityUiState.Loading ->
                    PageLoading(modifier = Modifier.fillMaxSize())

                is CityUiState.Success ->
                    CityList(
                        cities = uiState.data,
                        selectedCities = selectedCities,
                        toggleCityId = toggleCityId,
                    )
            }
        }

        CriteriaButton(
            navigateToStep2A = navigateToStep2A,
            navigateToStep2B = navigateToStep2B,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun Step1BodyPreview() {
    RekoWisataBaliTheme {
        Step1Body(
            navigateToStep2A = {},
            navigateToStep2B = {},
            uiState = CityUiState.Success(
                listOf(
                    City(1, "Bali"),
                    City(2, "Denpasar"),
                    City(3, "Gianyar")
                )
            ),
            selectedCities = setOf(1, 2),
            toggleCityId = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun CriteriaButton(
    navigateToStep2A: () -> Unit,
    navigateToStep2B: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Button(
            onClick = navigateToStep2A,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tentukan dengan template")
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            onClick = navigateToStep2B,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                "Tentukan secara manual",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun CityList(
    cities: List<City>,
    selectedCities: Set<Int>,
    toggleCityId: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Harap memilih setidaknya satu tempat",
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn {
            items(items = cities) { city ->
                CityCheckbox(
                    isChecked = selectedCities.contains(city.id),
                    cityName = city.name,
                    onCheckedChange = { toggleCityId(city.id) },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Preview
@Composable
fun CityListPreview() {
    val selectedCities = remember { mutableStateOf(setOf<Int>(1, 2)) }

    RekoWisataBaliTheme {
        CityList(
            cities = listOf(
                City(1, "Bali"),
                City(2, "Denpasar"),
                City(3, "Gianyar")
            ),
            selectedCities = selectedCities.value,
            toggleCityId = {
                if (selectedCities.value.contains(it)) {
                    selectedCities.value -= it
                } else {
                    selectedCities.value += it
                }
            }
        )
    }
}


@Composable
fun CityCheckbox(
    isChecked: Boolean,
    cityName: String,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onCheckedChange(!isChecked) }
    ) {
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        Text(text = cityName)
    }
}

@Preview
@Composable
fun CityCheckboxPreview() {
    RekoWisataBaliTheme {
        CityCheckbox(
            isChecked = true,
            cityName = "Bali",
            onCheckedChange = {}
        )
    }
}