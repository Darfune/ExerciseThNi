package com.example.threenitasapp.data.remote.downloader

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.os.Build
import androidx.core.net.toUri
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AndroidDownloader @Inject constructor(private val context: Context) : Downloader {

    val downloadManager = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.getSystemService(DownloadManager::class.java)
    } else {
        TODO("VERSION.SDK_INT < M")
    }
//    private val _percentage = MutableStateFlow(0L)
//    val percentage: StateFlow<Long> = _percentage.asStateFlow()


    private val _downloadProgress = MutableStateFlow(0f)
    val downloadProgress: Flow<Float> = _downloadProgress

    override fun downloadPDF(title: String, url: String): Long =
        downloadManager.enqueue(
            DownloadManager.Request(url.toUri())
                .setMimeType("application/pdf")
                .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setTitle("$title.pdf")
                .addRequestHeader("Authorization", "Bearer <toke>")
                .setDestinationInExternalFilesDir(
                    context,
                    "saved_pdfs",
                    "$title.pdf"
                )
        )


    @SuppressLint("Range")
    fun observeDownloadProgress(downloadId: Long) {
        val query = DownloadManager.Query().setFilterById(downloadId)
        val handler = CoroutineExceptionHandler { _, exception ->
            // Handle any exceptions that might occur during the download
            // For simplicity, you might want to show a toast or log the exception
            println("Download Failed: $exception")
        }

        CoroutineScope(Dispatchers.IO).launch(handler) {
            flow {
                while (true) {
                    val cursor = downloadManager.query(query)
                    if (cursor.moveToFirst()) {
                        val status =
                            cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                        if (status == DownloadManager.STATUS_SUCCESSFUL || status == DownloadManager.STATUS_FAILED) {
                            // Download completed or failed
                            break
                        }

                        val bytesDownloaded =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR))
                        val bytesTotal =
                            cursor.getLong(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES))

                        // Calculate the percentage
                        val downloadPercentage = (bytesDownloaded * 100f) / bytesTotal
                        emit(downloadPercentage)

                        delay(500) // Update every 500 milliseconds (adjust as needed)
                    }
                    cursor.close()
                }
            }.collect {
                _downloadProgress.value = it
            }
        }
    }
}