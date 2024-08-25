package com.lelestacia.lagidimana.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.domain.repository.MapRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MapViewModel(
    repository: MapRepository
) : ViewModel() {

    val last25Location: StateFlow<List<Location>> = repository.getLast25Location()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = emptyList()
        )
}