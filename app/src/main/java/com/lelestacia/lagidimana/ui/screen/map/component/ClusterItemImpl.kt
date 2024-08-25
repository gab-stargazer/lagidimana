package com.lelestacia.lagidimana.ui.screen.map.component

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

data class ClusterItemImpl(
    val location: LatLng,
    val isOnline: Boolean,
    val itemTitle: String?,
    val itemSnippet: String?,
    val address: String?,
    val timestamp: Long
) : ClusterItem {
    override fun getPosition(): LatLng {
        return location
    }

    override fun getTitle(): String? {
        return itemTitle
    }

    override fun getSnippet(): String? {
        return itemSnippet
    }

    override fun getZIndex(): Float? {
        return null
    }
}