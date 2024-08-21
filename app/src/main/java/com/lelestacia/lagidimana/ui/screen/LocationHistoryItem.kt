package com.lelestacia.lagidimana.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.lelestacia.lagidimana.ui.Location
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
            append("Koordinat: ")
        }
        append("${location.location.latitude}, ${location.location.longitude}")
    }

    val timeText = buildAnnotatedString {
        withStyle(
            MaterialTheme.typography.titleMedium.toSpanStyle()
        ) {
            append("Waktu: ")
        }
        append(Date(location.timeStamp).toTimeStandardIn12HoursWithoutSeconds())
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
    ) {
        Text(text = coordinateText)
        Text(text = timeText)
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