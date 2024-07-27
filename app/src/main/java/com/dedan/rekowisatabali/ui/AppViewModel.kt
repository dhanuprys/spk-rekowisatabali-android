package com.dedan.rekowisatabali.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.dedan.rekowisatabali.SpkApplication
import com.dedan.rekowisatabali.ui.screen.calculationform.CalculationFormViewModel
import com.dedan.rekowisatabali.ui.screen.home.HomeViewModel

object AppViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                spkApplication().container.recommendationHistoryRepository
            )
        }

        initializer {
            CalculationFormViewModel(
                spkApplication().container.placeRepository,
                spkApplication().container.recommendationHistoryRepository
            )
        }
    }
}

fun CreationExtras.spkApplication(): SpkApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SpkApplication)