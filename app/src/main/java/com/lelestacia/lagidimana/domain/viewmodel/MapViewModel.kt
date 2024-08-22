package com.lelestacia.lagidimana.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lelestacia.lagidimana.domain.repository.MapRepository
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.ui.state.MapScreenState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class MapViewModel(
    private val repository: MapRepository
) : ViewModel() {

    private val latestLocation = repository.getLiveLocation()

    private val last25Location = repository.getLast25Location()

    val mapScreenState =
        combine(latestLocation, last25Location) { latestLocation_, last25location_: List<Location> ->
            if (latestLocation_ != null) {
                MapScreenState(
                    lastLocation = latestLocation_,
                    last25Location = last25location_.toImmutableList()
                )
            } else {
                MapScreenState(
                    last25Location = last25location_.toImmutableList()
                )
            }
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(10000),
            MapScreenState()
        )
}