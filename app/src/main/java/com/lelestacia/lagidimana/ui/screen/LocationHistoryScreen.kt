package com.lelestacia.lagidimana.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lelestacia.lagidimana.domain.viewmodel.HistoryViewModel
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.ui.util.ChildRoute.History
import org.koin.androidx.compose.koinViewModel

@Composable
private fun LocationHistoryScreen(
    histories: LazyPagingItems<Location>,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        AnimatedContent(
            targetState = (histories.itemCount > 0),
            label = "Content Animation"
        ) {
            when (it) {
                true -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(count = histories.itemCount) { index ->
                            val location = histories[index]
                            location?.let {
                                LocationHistoryItem(location = location)
                                HorizontalDivider(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }
                }

                false -> {
                    Text(text = "Tidak ada riwayat lokasi")
                }
            }
        }
    }
}

fun NavGraphBuilder.locationHistoryScreen() {
    composable(History.route) {
        val vm = koinViewModel<HistoryViewModel>()
        val history = vm.locationHistory.collectAsLazyPagingItems()
        LocationHistoryScreen(histories = history, modifier = Modifier.fillMaxSize())
    }
}