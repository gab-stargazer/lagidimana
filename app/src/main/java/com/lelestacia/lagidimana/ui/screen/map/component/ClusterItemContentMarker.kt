package com.lelestacia.lagidimana.ui.screen.map.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lelestacia.lagidimana.ui.theme.Purple40

@Composable
fun ClusterItemContentMarker(
    item: ClusterItemImpl,
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector =
        if (item.isOnline) {
            Icons.Default.LocationOn
        } else {
            Icons.Default.LocationOff
        },
        contentDescription = null,
        tint =
        if (!item.isOnline) {
            Color.Red.copy(0.75F)
        } else {
            Color.Green.copy(0.75F)
        },
        modifier = modifier
    )
}

@Composable
fun ClusterItemContentMarkerLastLocation(
    modifier: Modifier = Modifier
) {
    Icon(
        imageVector = Icons.Default.LocationOn,
        contentDescription = null,
        tint = Purple40,
        modifier = modifier
    )
}