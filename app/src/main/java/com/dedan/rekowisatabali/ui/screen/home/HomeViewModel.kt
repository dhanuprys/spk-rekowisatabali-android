package com.dedan.rekowisatabali.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dedan.rekowisatabali.data.repository.RecommendationHistoryRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    private val recommendationHistoryRepository: RecommendationHistoryRepository
) : ViewModel() {
    val recommendationHistory = recommendationHistoryRepository.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
}