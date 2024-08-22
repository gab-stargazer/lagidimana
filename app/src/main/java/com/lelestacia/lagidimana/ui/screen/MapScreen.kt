package com.lelestacia.lagidimana.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOff
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.lelestacia.lagidimana.domain.model.toClusterItem
import com.lelestacia.lagidimana.domain.viewmodel.MapViewModel
import com.lelestacia.lagidimana.ui.state.MapScreenState
import com.lelestacia.lagidimana.ui.theme.Purple40
import com.lelestacia.lagidimana.ui.util.ChildRoute.Map
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(MapsComposeExperimentalApi::class)
@Composable
private fun MapScreen(
    state: MapScreenState,
    modifier: Modifier = Modifier
) {

    val scope = rememberCoroutineScope()

    val cameraPositionState = rememberCameraPositionState()
    val context = LocalContext.current

    LaunchedEffect(state.lastLocation) {
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLng(state.lastLocation)
        )
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
            minZoomPreference = 10F
        ),
        onMapLoaded = {
            scope.launch {
                cameraPositionState.animate(CameraUpdateFactory.newLatLng(state.lastLocation))
            }
        },
        modifier = modifier
    ) {
        Clustering(
            items = state.last25Location.mapIndexed { index, location ->
                if (index == 0) {
                    location.toClusterItem(
                        title = "Last Known Location",
                        snippet = location.address
                    )
                } else {
                    location.toClusterItem()
                }
            },
            clusterContent = { cluster ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "${cluster.size} dikunjungi",
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
            },
            clusterItemContent = { item ->
                if (item.itemTitle.isNullOrBlank()) {
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
                            Color.Red.copy(0.5F)
                        } else {
                            Color.Green.copy(0.5F)
                        }
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
//                        ElevatedCard(
//                            shape = RoundedCornerShape(8),
//                            colors = CardDefaults.elevatedCardColors(
//                                containerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
//                                contentColor = MaterialTheme.colorScheme.onSurface
//                            ),
//                            modifier = Modifier.widthIn(max = 250.dp)
//                        ) {
//                            Column(
//                                modifier = Modifier.padding(12.dp)
//                            ) {
//                                Text(
//                                    text = "Posisi terakhir",
//                                    textAlign = TextAlign.Center,
//                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//                                    modifier = Modifier.fillMaxWidth()
//                                )
//
//                                val visitedAt = buildAnnotatedString {
//                                    withStyle(MaterialTheme.typography.titleMedium.toSpanStyle()) {
//                                        append("Dikunjungi pada: ")
//                                    }
//
//                                    withStyle(MaterialTheme.typography.bodyMedium.toSpanStyle()) {
//                                        append(Date(item.timestamp).toTimeStandardIn12HoursWithoutSeconds())
//                                    }
//                                }
//
//                                Text(text = visitedAt)
//
//                                item.address?.let {
//                                    Text(
//                                        text = it,
//                                        style = MaterialTheme.typography.bodyMedium,
//                                        textAlign = TextAlign.Justify
//                                    )
//                                }
//                            }
//                        }

                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Purple40
                        )
                    }
                }
            }
        )
//        state.last25Location.forEachIndexed { index, location ->
//            Marker(
//                state = rememberMarkerState(position = location.location),
//                title =
//                if (index == 0) {
//                    "Last Known Location"
//                } else {
//                    null
//                }
//            )
//        }
    }
}

fun NavGraphBuilder.mapScreen() {
    composable(Map.route) {
        val vm: MapViewModel = koinViewModel()
        val state by vm.mapScreenState.collectAsStateWithLifecycle()

        MapScreen(
            state = state,
            modifier = Modifier.fillMaxSize()
        )
    }
}