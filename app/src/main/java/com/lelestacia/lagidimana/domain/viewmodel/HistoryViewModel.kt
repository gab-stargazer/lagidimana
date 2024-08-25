package com.lelestacia.lagidimana.domain.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.lelestacia.lagidimana.domain.repository.MapRepository

class HistoryViewModel(private val repository: MapRepository) : ViewModel() {

    val locationHistory = repository.getHistoryLocation().cachedIn(viewModelScope)
}