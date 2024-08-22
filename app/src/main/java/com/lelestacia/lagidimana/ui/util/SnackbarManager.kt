package com.lelestacia.lagidimana.ui.util

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.LinkedList
import java.util.concurrent.atomic.AtomicReference

class SnackbarManager(
    val snackbarHostState: SnackbarHostState,
) {

    private val mutex = Mutex()
    private val isRunning: AtomicReference<Boolean> = AtomicReference()
    private val snackbarQueue = LinkedList<SnackbarData>()

    suspend fun queue(snackbarData: SnackbarData) {
        mutex.withLock {
            snackbarQueue.add(snackbarData)
            if (isRunning.compareAndSet(false, true)) {
                startDequeue()
            }
        }
    }

    private suspend fun startDequeue() {
        val poppedItem = snackbarQueue.pop()

        snackbarHostState.showSnackbar(
            poppedItem.message,
            poppedItem.actionLabel,
            poppedItem.withDismissAction,
            poppedItem.duration
        )

        if (snackbarQueue.isEmpty()) isRunning.set(false)
        else startDequeue()
    }

    data class SnackbarData(
        val message: String,
        val duration: SnackbarDuration = SnackbarDuration.Short,
        val withDismissAction: Boolean = false,
        val actionLabel: String? = null,
    )

    init {
        isRunning.set(false)
    }
}