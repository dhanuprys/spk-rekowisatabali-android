package com.dedan.rekowisatabali.ui.screen.calculationform

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedan.rekowisatabali.data.PlaceRepository
import com.dedan.rekowisatabali.model.City
import com.dedan.rekowisatabali.model.PlaceRecommendation
import com.dedan.rekowisatabali.model.RecommendationMetric
import com.dedan.rekowisatabali.model.RecommendationTemplate
import com.dedan.rekowisatabali.model.RecommendationTemplateRequest
import kotlinx.coroutines.launch

class CalculationFormViewModel(
    private val placeRepository: PlaceRepository
) : ViewModel() {
    private var calculationType: CalculationType by mutableStateOf(CalculationType.Unknown)
    private var recommendationMetric by mutableStateOf(generateDefaultMetric())
    var selectedCities by mutableStateOf(setOf<Int>())
    var cityUiState: CityUiState by mutableStateOf(CityUiState.Loading)
        private set
    var recommendationTemplateUiState: RecommendationTemplateUiState
            by mutableStateOf(RecommendationTemplateUiState.Loading)
        private set
    var resultUiState: ResultUiState by mutableStateOf(ResultUiState.Loading)
        private set
    var reportOptionsUiState: ReportOptionsUiState by mutableStateOf(ReportOptionsUiState())
        private set

    fun resetCalculation() {
        calculationType = CalculationType.Unknown
        recommendationMetric = generateDefaultMetric()
        selectedCities = setOf()
        cityUiState = CityUiState.Loading
        recommendationTemplateUiState = RecommendationTemplateUiState.Loading
        resultUiState = ResultUiState.Loading
        reportOptionsUiState = ReportOptionsUiState()
    }

    fun resetCriteria() {
        recommendationMetric = generateDefaultMetric()
    }

    fun prepareTemplateCalculation(templateId: Int) {
        calculationType = CalculationType.Template(selectedCities, templateId)
    }

    fun prepareManualCalculation() {
        calculationType = CalculationType.Manual(recommendationMetric)
    }

    fun getResult() {
        viewModelScope.launch {
            resultUiState = ResultUiState.Loading
            resultUiState = try {
                when (calculationType) {
                    is CalculationType.Manual -> {
                        val manualCalculationType = calculationType as CalculationType.Manual
                        val recommendations = placeRepository.calculateManualRecomendations(
                            manualCalculationType.recommendationMetric.copy(
                                cities_id = selectedCities,
                                limit = reportOptionsUiState.recommendationLimit
                            )
                        )

                        ResultUiState.Success(recommendations)
                    }
                    is CalculationType.Template -> {
                        val templateCalculationType = calculationType as CalculationType.Template
                        val recommendations = placeRepository.calculateTemplateRecomendations(
                            RecommendationTemplateRequest(
                                reportOptionsUiState.recommendationLimit,
                                selectedCities,
                                templateCalculationType.templateId
                            )
                        )

                        Log.d("CalculationFormViewModel", "recommendations: $recommendations")
                        ResultUiState.Success(recommendations)
                    }

                    else -> ResultUiState.Error
                }
            } catch (e: Exception) {
                Log.e("CalculationFormViewModel", "Error fetching manual recommendations", e)
                ResultUiState.Error
            }
        }
    }

    fun reorderCriteria(criteria: List<Pair<String, Float>>) {
        criteria.forEach { item ->
            recommendationMetric = when (item.first) {
                "l1_a" -> recommendationMetric.copy(inp_l1_a = item.second)
                "l1_b" -> recommendationMetric.copy(inp_l1_b = item.second)
                "l1_c" -> recommendationMetric.copy(inp_l1_c = item.second)
                "l2_cg1_a" -> recommendationMetric.copy(inp_l2_cg1_a = item.second)
                "l2_cg1_b" -> recommendationMetric.copy(inp_l2_cg1_b = item.second)
                "l2_cg1_c" -> recommendationMetric.copy(inp_l2_cg1_c = item.second)
                "l3_cg1_a" -> recommendationMetric.copy(inp_l3_cg1_a = item.second)
                "l3_cg1_b" -> recommendationMetric.copy(inp_l3_cg1_b = item.second)
                "l3_cg2_a" -> recommendationMetric.copy(inp_l3_cg2_a = item.second)
                "l3_cg2_b" -> recommendationMetric.copy(inp_l3_cg2_b = item.second)
                "l3_cg2_c" -> recommendationMetric.copy(inp_l3_cg2_c = item.second)
                "l3_cg3_a" -> recommendationMetric.copy(inp_l3_cg3_a = item.second)
                "l3_cg3_b" -> recommendationMetric.copy(inp_l3_cg3_b = item.second)
                else -> recommendationMetric
            }
        }
    }

    fun changeCriteriaDirection(pair: Pair<String, Int>) {
        when (pair.first) {
            "l1_b" -> recommendationMetric = recommendationMetric.copy(l1_b_direction = pair.second)
            "l1_c" -> recommendationMetric = recommendationMetric.copy(l1_c_direction = pair.second)
        }
    }

    fun setReportLimit(limit: Int) {
        reportOptionsUiState = reportOptionsUiState.copy(recommendationLimit = limit)
    }

    fun setGraphReport(withGraphReport: Boolean) {
        reportOptionsUiState = reportOptionsUiState.copy(withGraphReport = withGraphReport)
    }

    fun setTableReport(withTableReport: Boolean) {
        reportOptionsUiState = reportOptionsUiState.copy(withTableReport = withTableReport)
    }

    fun getRecommendationTemplates() {
        viewModelScope.launch {
            recommendationTemplateUiState = try {
                val templates = placeRepository.getRecommendationTemplates()
                RecommendationTemplateUiState.Success(templates)
            } catch (e: Exception) {
                Log.e("CalculationFormViewModel", "Error fetching recommendation templates", e)
                RecommendationTemplateUiState.Error
            }
        }
    }

    fun getCities() {
        viewModelScope.launch {
            cityUiState = try {
                val cities = placeRepository.getCities()
                CityUiState.Success(cities)
            } catch (e: Exception) {
                CityUiState.Error
            }
        }
    }

    fun toggleCityId(cityId: Int) {
        selectedCities = if (selectedCities.contains(cityId)) {
            selectedCities - cityId
        } else {
            selectedCities + cityId
        }
    }
}

sealed interface CalculationType {
    object Unknown : CalculationType
    data class Manual(
        val recommendationMetric: RecommendationMetric
    ) : CalculationType

    data class Template(
        val citiesId: Set<Int>,
        val templateId: Int
    ) : CalculationType
}

sealed interface CityUiState {
    object Loading : CityUiState
    object Error : CityUiState
    data class Success(val data: List<City>) : CityUiState
}

sealed interface RecommendationTemplateUiState {
    object Loading : RecommendationTemplateUiState
    object Error : RecommendationTemplateUiState
    data class Success(val data: List<RecommendationTemplate>) : RecommendationTemplateUiState
}

data class ReportOptionsUiState(
    val recommendationLimit: Int = 10,
    val withGraphReport: Boolean = true,
    val withTableReport: Boolean = true
)

sealed interface ResultUiState {
    object Loading : ResultUiState
    object Error : ResultUiState
    data class Success(val data: List<PlaceRecommendation>) : ResultUiState
}

private fun generateDefaultMetric(): RecommendationMetric =
    RecommendationMetric(
        limit = 10,
        cities_id = setOf(),
        inp_l3_cg1_a = 0.75f,
        inp_l3_cg1_b = 0.25f,
        inp_l3_cg2_a = 0.6111f,
        inp_l3_cg2_b = 0.2778f,
        inp_l3_cg2_c = 0.1111f,
        inp_l3_cg3_a = 0.6111f,
        inp_l3_cg3_b = 0.2778f,
        inp_l3_cg3_c = 0.1111f,
        inp_l2_cg1_a = 0.6111f,
        inp_l2_cg1_b = 0.2778f,
        inp_l2_cg1_c = 0.1111f,
        inp_l1_a = 0.6111f,
        inp_l1_b = 0.2778f,
        inp_l1_c = 0.1111f,
        l1_b_direction = 1,
        l1_c_direction = 1
    )
