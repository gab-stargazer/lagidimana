package com.lelestacia.lagidimana.ui.screen.history

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.lelestacia.lagidimana.R
import com.lelestacia.lagidimana.domain.model.Location
import com.lelestacia.lagidimana.domain.viewmodel.HistoryViewModel
import com.lelestacia.lagidimana.ui.util.ChildRoute.History
import org.koin.androidx.compose.koinViewModel

@Composable
private fun LocationHistoryScreen(
    histories: LazyPagingItems<Location>,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        if (histories.loadState.refresh == LoadState.Loading) {
            CircularProgressIndicator()
        } else {
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
                        Text(text = stringResource(R.string.tv_no_location_history))
                    }
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