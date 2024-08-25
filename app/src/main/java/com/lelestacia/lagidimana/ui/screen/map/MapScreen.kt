package com.lelestacia.lagidimana.ui.screen.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MapsComposeExperimentalApi
import com.google.maps.android.compose.clustering.Clustering
import com.google.maps.android.compose.rememberCameraPositionState
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.domain.model.toClusterItem
import com.lelestacia.lagidimana.domain.viewmodel.MapViewModel
import com.lelestacia.lagidimana.ui.screen.map.component.ClusterItemContentMarker
import com.lelestacia.lagidimana.ui.screen.map.component.ClusterItemContentMarkerLastLocation
import com.lelestacia.lagidimana.ui.screen.map.component.ClusterItemMarker
import com.lelestacia.lagidimana.ui.util.ChildRoute.Map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun MapScreen(
    last25Location: List<Location>,
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current

    LaunchedEffect(last25Location) {
        last25Location.firstOrNull()?.let { lastLocation ->
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(lastLocation.location, 15F)
            )
        }
    }

    GoogleMap(
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(
            compassEnabled = false,
            indoorLevelPickerEnabled = false,
            rotationGesturesEnabled = false,
        ),
        properties = MapProperties(
            mapStyleOptions = MapStyleOptions.loadRawResourceStyle(context, R.raw.map_style),
            minZoomPreference = 5F
        ),
        onMapLoaded = {
            scope.launch {
                last25Location.firstOrNull()?.let { lastLocation ->
                    cameraPositionState.animate(
                        CameraUpdateFactory.newLatLngZoom(lastLocation.location, 15F)
                    )
                }
            }
        },
        modifier = modifier
    ) {
        Clustering(
            items = last25Location.mapIndexed { index, location ->
                if (index == 0) {
                    location.toClusterItem(
                        title = stringResource(R.string.tv_last_known_location),
                        snippet = location.address
                    )
                } else {
                    location.toClusterItem()
                }
            },
            clusterContent = { cluster ->
                ClusterItemMarker(cluster = cluster)
            },
            clusterItemContent = { item ->
                if (item.itemTitle.isNullOrBlank()) {
                    ClusterItemContentMarker(item = item)
                } else {
                    ClusterItemContentMarkerLastLocation()
                }
            }
        )
    }
}

fun NavGraphBuilder.mapScreen() {
    composable(Map.route) {
        val vm: MapViewModel = koinViewModel()
        val last25Location by vm.last25Location.collectAsStateWithLifecycle()

        MapScreen(
            last25Location = last25Location,
            modifier = Modifier.fillMaxSize()
        )
    }
}