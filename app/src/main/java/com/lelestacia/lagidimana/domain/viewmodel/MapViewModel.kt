package com.lelestacia.lagidimana.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.domain.repository.MapRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MapViewModel(
    private val repository: MapRepository
) : ViewModel() {

    val latestLocation: StateFlow<LatLng?> = repository.getLiveLocation().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = null
    )


}