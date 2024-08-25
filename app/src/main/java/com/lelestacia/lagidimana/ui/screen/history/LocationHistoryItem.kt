package com.lelestacia.lagidimana.ui.screen.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.ui.theme.LagiDimanaTheme
import com.parassidhu.simpledate.toTimeStandardIn12HoursWithoutSeconds
import java.util.Date

@Composable
fun LocationHistoryItem(
    location: Location,
    modifier: Modifier = Modifier
) {
    val coordinateText = buildAnnotatedString {
        withStyle(
            MaterialTheme.typography.titleMedium.toSpanStyle()
        ) {
            if (location.address.isNullOrBlank()) {
                append(stringResource(R.string.tv_coordinate))
            } else {
                append(stringResource(R.string.tv_location))
            }
        }
        if (location.address.isNullOrBlank()) {
            append("${location.location.latitude}, ${location.location.longitude}")
        } else {
            append(location.address)
        }
    }

    val timeText = buildAnnotatedString {
        withStyle(
            MaterialTheme.typography.titleMedium.toSpanStyle()
        ) {
            append(stringResource(R.string.tv_time))
        }
        append(Date(location.timeStamp).toTimeStandardIn12HoursWithoutSeconds())
    }

    val connectionStatusText = buildAnnotatedString {
        withStyle(
            MaterialTheme.typography.titleMedium.toSpanStyle()
        ) {
            append(stringResource(R.string.tv_status))
        }
        if(location.isOnline) {
            append(stringResource(R.string.tv_connected_to_internet))
        } else {
            append(stringResource(R.string.tv_not_connected_to_internet))
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Column(
            modifier = modifier.weight(1f)
        ) {
            Text(text = timeText)
            Text(text = coordinateText)
            Text(text = connectionStatusText)
        }
    }
}

@Preview
@PreviewLightDark
@Composable
private fun PreviewLocationHistoryItem() {
    LagiDimanaTheme {
        Surface {
            LocationHistoryItem(
                location = Location(
                    location = LatLng(37.7749, -122.4194),
                    timeStamp = 1625097600000
                )
            )
        }
    }
}