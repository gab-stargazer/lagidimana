package com.lelestacia.lagidimana.ui.screen.map.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.compose.GoogleMapComposable
import com.lelestacia.lagidimana.R

@Composable
@GoogleMapComposable
fun ClusterItemMarker(
    cluster: Cluster<ClusterItemImpl>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.marker_cluster_title, cluster.size),
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color.Black
            )
        )
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            tint = Color.Blue.copy(0.75F)
        )
    }
}